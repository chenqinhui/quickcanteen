package com.quickcanteen.quickcanteen.activities.collect;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.collect.CollectActionImpl;
import com.quickcanteen.quickcanteen.actions.collect.ICollectAction;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.search.SearchActivity;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity {
    private ICollectAction collectAction;
    private List<DishesBean> collectList = new ArrayList<>();
    private RecyclerView collectView;
    private LinearLayoutManager layoutManager;
    private CollectAdapter collectAdapter;
    private TextView noneCollectAttention;
    private int pageNumber = 0;
    private int pageSize = 5;
    private int lastVisibleItem = 5;

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        BaseActivity.initializeTop(this, true, "我的收藏");

        collectAction = new CollectActionImpl(this);

        collectView = (RecyclerView) findViewById(R.id.myCollectList);
        noneCollectAttention = (TextView)findViewById(R.id.noneCollections);
        layoutManager = new LinearLayoutManager(this);
        collectView.setLayoutManager(layoutManager);

        new Thread(new CollectThread()).start();
        collectAdapter = new CollectAdapter(collectList,this);
        collectView.setAdapter(collectAdapter);
        collectView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 ==collectAdapter.getItemCount()){
                    new Thread(new CollectThread()).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        if(collectList.size()==0)
            noneCollectAttention.setVisibility(View.VISIBLE);
        else
            noneCollectAttention.setVisibility(View.GONE);
    }

    public class CollectThread implements Runnable {
        @Override
        public void run() {
            try {
                //获取收藏菜的列表DishesBeanList
                BaseJson baseJson=collectAction.getCollectDishesByUserId(pageNumber,pageSize);
                List<DishesBean> newCollectBeans = new ArrayList<>();
                JSONArray jsonArray = baseJson.getJSONArray();
                switch (baseJson.getReturnCode()) {
                    case "8.0":
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                            DishesBean collectItem = new DishesBean((tempJsonObject));
                            newCollectBeans.add(collectItem);
                        }
                        pageNumber++;
                        collectList.addAll(newCollectBeans);
                        break;
                    case "8.0.E.1":
                        baseJson.getErrorMessage();
                        break;
                    default:
                        break;
                }
            } catch (final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CollectActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    collectAdapter.notifyDataSetChanged();
                    noneCollectAttention.setVisibility(View.GONE);
                }
            });
        }
    }
}

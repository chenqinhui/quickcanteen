package com.quickcanteen.quickcanteen.activities.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.search.ISearchAction;
import com.quickcanteen.quickcanteen.actions.search.impl.SearchActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.bean.SearchBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private RecyclerView searchResult;
    private LinearLayoutManager linearLayoutManager;
    private SearchResultAdapter adapter;
    private List<SearchBean> searchBeans = new ArrayList<>();
    private Button searchBtn2;
    private EditText searchEditText2;
    private ISearchAction searchAction=new SearchActionImpl(this);

    private static Handler handler = new Handler();
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BaseActivity.initializeTop(this, true, "搜索");
        searchBtn2 = (Button) findViewById(R.id.search_btn2);
        searchEditText2 = (EditText) findViewById(R.id.search_text_input2);
        searchResult = (RecyclerView) findViewById(R.id.search_result_list);
        linearLayoutManager = new LinearLayoutManager(this);
        searchResult.setLayoutManager(linearLayoutManager);
        adapter = new SearchResultAdapter(searchBeans, this);
        searchResult.setAdapter(adapter);
        searchBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyStr2 = searchEditText2.getText().toString();
                if (keyStr2.isEmpty()){
                    Toast.makeText(v.getContext(), "请先输入关键词", Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new SearchThread(keyStr2)).start();
                }
            }
        });
    }

    public class SearchThread implements Runnable {
        private String key;

        public SearchThread(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            try {
                //连接网络的耗时操作（不能再主线程里做）
                BaseJson baseJson=searchAction.search(key);
                JSONArray jsonArray = baseJson.getJSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                    searchBeans.add(new SearchBean(tempJsonObject));
                }
                //更改UI的操作（必须由主线程/UI线程做）
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            } catch (final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("SearchActivity","查找成功。");
                }
            });
        }
    }
}

package com.quickcanteen.quickcanteen.fragment.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.main.IMainAction;
import com.quickcanteen.quickcanteen.actions.main.impl.MainActionImpl;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.search.SearchActivity;
import com.quickcanteen.quickcanteen.bean.CompanyInfoBean;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private static Handler handler = new Handler();
    private List<CompanyInfoBean> canteenList = new ArrayList<>();
    private RecyclerView canteenView;
    private LinearLayoutManager layoutManager;
    private CanteenListAdapter canteenAdapter;
    private int pageNumber = 0;
    private int pageSize = 5;
    private int lastVisibleItem = 5;
    private IMainAction mainAction;
    private TextView noneCompanyAttention;

    private RecyclerView recommendView;
    private RecommendAdapter recommendAdapter;
    private LinearLayoutManager recommendLayoutManager;
    private List<DishesBean> recommendList = new ArrayList<>();

    private Spinner chooseCompany;
    private List<String> company = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String selected;

    private SearchView searchBtn;

    String message = "查找内容";//动态监听EditText
    String comp = "商家名称";//监听Spinner

    private Button button;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_page, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainAction = new MainActionImpl(getActivity());
        chooseCompany = (Spinner)getActivity().findViewById(R.id.chooseCompany);
        //测试数据
        company.add("河西食堂");
        company.add("河东食堂");
        company.add("丽娃食堂");

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_style,R.id.mainPageSpinner, company);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_style);
        chooseCompany.setAdapter(adapter);
        chooseCompany.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                selected = adapter.getItem(position);
                Toast.makeText(getActivity(),selected, Toast.LENGTH_SHORT).show();
                parent.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(),"无", Toast.LENGTH_SHORT).show();
                parent.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        chooseCompany.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        chooseCompany.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });



        searchBtn = (SearchView)getActivity().findViewById(R.id.searchView);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("searchCompany",comp);
                bundle.putString("searchStr",message);
                intent.putExtras(bundle);

                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        /*推荐菜列表*/
        recommendView = (RecyclerView)getActivity().findViewById(R.id.recommendList);
        recommendLayoutManager = new LinearLayoutManager(getActivity());
        recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recommendView.setLayoutManager(recommendLayoutManager);

        recommendAdapter = new RecommendAdapter(recommendList,getActivity());
        recommendView.setAdapter(recommendAdapter);
        new Thread(new RecommendThread()).start();

        /*餐厅列表*/
        canteenView = (RecyclerView) getActivity().findViewById(R.id.canteenList);
        noneCompanyAttention = (TextView)getActivity().findViewById(R.id.noneCanteenAttention);
        layoutManager = new LinearLayoutManager(getActivity());
        canteenView.setLayoutManager(layoutManager);

        canteenAdapter = new CanteenListAdapter(canteenList,getActivity());
        canteenView.setAdapter(canteenAdapter);
        canteenView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 ==canteenAdapter.getItemCount()){
                    new Thread(new MainThread()).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        new Thread(new MainThread()).start();
        if(canteenList.size()!=0)
            noneCompanyAttention.setVisibility(View.GONE);
    }

    public class MainThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = mainAction.getCompanyInfoByPage(pageNumber,pageSize);
                List<CompanyInfoBean> newCompanyInfoBeans = new ArrayList<>();
                JSONArray jsonArray = baseJson.getJSONArray();
                switch (baseJson.getReturnCode()) {
                    case "4.0":
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                            CompanyInfoBean companyInfoBean = new CompanyInfoBean((tempJsonObject));
                            newCompanyInfoBeans.add(companyInfoBean);
                        }
                        pageNumber++;
                        canteenList.addAll(newCompanyInfoBeans);
                        break;
                    case "4.0.E.1":
                        baseJson.getErrorMessage();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });

                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    canteenAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public class RecommendThread implements Runnable{
        @Override
        public void run(){
            try{
                BaseJson baseJson = mainAction.getRecommendListByUserId();
                JSONArray jsonArray = baseJson.getJSONArray();
                switch (baseJson.getReturnCode()) {
                    case "10.0":
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                            DishesBean dishesBean = new DishesBean((tempJsonObject));
                            recommendList.add(dishesBean);
                        }
                        break;
                    case "10.0.E.1":
                        baseJson.getErrorMessage();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    recommendAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /*实现点击特定推荐菜按钮完成从主页面跳到菜品详情页面*/
    class recommendDishListener implements View.OnClickListener {
        private int dishesID;
        public recommendDishListener(int dishesID){
            this.dishesID=dishesID;
        }
        @Override
        public void onClick(View arg0){
            Intent intent =new Intent();
            Bundle bundle=new Bundle();
            bundle.putInt("dishesID",dishesID);
            intent.putExtras(bundle);
            //intent.setClass(MainPage.this, DishesIntroduction_1.class);
            startActivity(intent);
        }

    }

    public static MainFragment newInstance() {
       return new MainFragment();
    }

}

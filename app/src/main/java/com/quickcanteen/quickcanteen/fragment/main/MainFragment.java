package com.quickcanteen.quickcanteen.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.company.ICompanyAction;
import com.quickcanteen.quickcanteen.actions.company.impl.CompanyActionImpl;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.search.SearchActivity;
import com.quickcanteen.quickcanteen.bean.CompanyInfoBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFragment extends Fragment {

    private static Handler handler = new Handler();

    /*三个食堂的按钮*/
    private ImageButton canteen1 = null;
    private Button canteenText1 = null;

    private Spinner chooseCompany;
    private List<String> company = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String selected;

    private EditText searchDishes;
    private TextView notUse;
    private SearchView searchBtn;

    String message = "查找内容";//动态监听EditText
    String comp = "商家名称";//监听Spinner

    private List<Map<String, Object>> canteenData;
    private List<CompanyInfoBean> detailCanteenList = new ArrayList<CompanyInfoBean>();
    private ListView listView;
    private MyAdapter canteenAdapter;

    private ICompanyAction companyAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_page, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        companyAction= new CompanyActionImpl(getActivity());
        chooseCompany = (Spinner) getActivity().findViewById(R.id.chooseCompany);
        //测试数据
        company.add("河西食堂");
        company.add("河东食堂");
        company.add("丽娃食堂");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_style, R.id.mainPageSpinner, company);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_style);
        chooseCompany.setAdapter(adapter);
        chooseCompany.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                selected = adapter.getItem(position);
                Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
                parent.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "无", Toast.LENGTH_SHORT).show();
                parent.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        chooseCompany.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        chooseCompany.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

       /* notUse = (TextView)getActivity().findViewById(R.id.text_notuse);
        notUse.requestFocus();*/


        searchBtn = (SearchView) getActivity().findViewById(R.id.searchView);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /*Bundle bundle=new Bundle();
                bundle.putString("searchCompany",comp);
                bundle.putString("searchStr",message);
                intent.putExtras(bundle);*/

                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        /*canteen1 = (ImageButton) findViewById(R.id.canteen1);
        canteen1.setOnClickListener(new canteenListener(1));*/

        /*推荐菜列表*/
        LinearLayout recommendLayout;
        recommendLayout = (LinearLayout) getActivity().findViewById(R.id.RecommendLayout);
        for (int i = 0; i < 5; i++) {
            final View recommendView = createView();
            recommendLayout.addView(recommendView);
        }

        /*餐厅列表*/
        listView = (ListView) getActivity().findViewById(R.id.canteenList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                skip(position);
            }
        });

        new Thread(new MyThread()).start();
        canteenData = getData();
        canteenAdapter = new MyAdapter(getActivity());
        listView.setAdapter(canteenAdapter);

    /*为推荐菜按钮设置监听器*/

    /*为食堂按钮设置监听器*/

    }

    public View createView() {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(15, 5, 15, 10);
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(350, 350));
        Bitmap map = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu1);
        imageView.setImageBitmap(map);
        TextView textView = new TextView(getActivity());
        textView.setText("炖猪蹄");
        textView.setGravity(Gravity.CENTER);
        linearLayout.setTag(textView.getText());
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        return linearLayout;
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson=companyAction.getCompanyInfoByCompanyId(1);
                CompanyInfoBean companyInfoBean=new CompanyInfoBean(baseJson.getJSONObject());
                detailCanteenList.add(companyInfoBean);
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
                    canteenData = getData();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    //获取动态数组数据  可以由其他地方传来(json等)
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> canteenList = new ArrayList<Map<String, Object>>();
        TextView attention = (TextView) getActivity().findViewById(R.id.noneCanteenAttention);
        for (CompanyInfoBean companyInfo : detailCanteenList) {
            Map<String, Object> map = new HashMap<String, Object>();
            int rating = 4;
            map.put("name", companyInfo.getCompanyName());
            map.put("info", "可配送、可自取");
            map.put("rating", rating);
            map.put("favourable", "活动信息");
            canteenList.add(map);
        }
        if (canteenList.size() == 0) {
            attention.setVisibility(0);
        } else {
            attention.setVisibility(8);
        }
        return canteenList;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return canteenData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //****************************************final方法
        //注意原本getView方法中的int position变量是非final的，现在改为final
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.canteen_list, null);
                holder.name = (TextView) convertView.findViewById(R.id.canteenName);
                holder.information = (TextView) convertView.findViewById(R.id.takeWay);
                holder.rating = (RatingBar) convertView.findViewById(R.id.canteenRating);
                holder.favorable = (TextView) convertView.findViewById(R.id.favourableActivity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(canteenData.get(position).get("name").toString());
            holder.information.setText(canteenData.get(position).get("info").toString());
            holder.rating.setRating((int) canteenData.get(position).get("rating"));

            return convertView;
        }
    }

    //list每一条目包含控件
    public final class ViewHolder {
        public TextView name;
        public RatingBar rating;
        public TextView information;
        public TextView favorable;
    }

    /*实现点击特定推荐菜按钮完成从主页面跳到菜品详情页面*/
    class recommendDishListener implements View.OnClickListener {
        private int dishesID;

        public recommendDishListener(int dishesID) {
            this.dishesID = dishesID;
        }

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("dishesID", dishesID);
            intent.putExtras(bundle);
            //intent.setClass(MainPage.this, DishesIntroduction_1.class);
            startActivity(intent);
        }

    }

    /*从餐厅按钮点击进入相应点菜界面*/
    public void skip(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        //bundle.putInt("companyID",detailCanteenList.get(position).getComanyId());
        bundle.putInt("companyID", 1);
        bundle.putString("companyName", "河西食堂");
        intent.putExtras(bundle);
        intent.setClass(getActivity(), CanteenActivity.class);
        startActivity(intent);
    }

    /*实现点击特定食堂按钮完成从主页面跳转到相应食堂页面,此处先用某个页面CanteenPage代替所有食堂页面*/
    /*class canteenListener implements View.OnClickListener {
        private int companyID;
        public canteenListener(int companyID){
            this.companyID=companyID;
        }
        @Override
        public void onClick(View arg0){
            Intent intent =new Intent();
            Bundle bundle=new Bundle();
            bundle.putInt("companyID",companyID);
            bundle.putString("companyName","河西食堂");
            intent.putExtras(bundle);
            intent.setClass(MainPage.this, CanteenPage.class);
            startActivity(intent);
        }

    }*/

    public static MainFragment newInstance() {
        return new MainFragment();
    }

}

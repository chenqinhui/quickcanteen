package com.quickcanteen.quickcanteen.activities.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.bean.SearchBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 11022 on 2017/7/3.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> implements View.OnClickListener {
    private List<SearchBean> searchBeans;
    private Context context;
    private Activity activity;

    public SearchResultAdapter(List<SearchBean> searchBeans, Activity activity) {
        this.searchBeans = searchBeans;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchBean searchBean = searchBeans.get(position);
        /*Bitmap bitmap = AsyncBitmapLoader.asyncBitmapLoader.loadBitmap( holder.searchPic, searchBean.getCompanyInfoBean()., holder.searchPic.getLayoutParams().width, holder.searchPic.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            }
        });
        if(bitmap!=null){
            holder.searchPic.setImageBitmap(bitmap);
        }*/
        holder.view.setTag(position);
        holder.searchCompany.setText(searchBean.getCompanyInfoBean().getCompanyName());
        if(searchBean.getHasDishes()){
            SimpleAdapter adapter = new SimpleAdapter(context, getData(searchBean.getDishesBeans()), R.layout.search_dishes_list,
                    new String[]{"name", "price"},
                    new int[]{R.id.dishes_name, R.id.dishes_price});
            holder.searchDishesList.setAdapter(adapter);
        }
    }

    private ArrayList<HashMap<String, Object>> getData(List<DishesBean> list) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (DishesBean temp : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", temp.getDishesName());
            map.put("price", temp.getPrice()+"元");
            arrayList.add(map);
        }
        return arrayList;
    }


    @Override
    public int getItemCount() {
        return searchBeans.size();
    }

    @Override
    public void onClick(View v) {
        toAddCart(searchBeans.get((int)v.getTag()));
    }

    public void toAddCart(SearchBean searchBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("companyId",searchBean.getCompanyInfoBean().getCompanyId());
        intent.putExtras(bundle);
        intent.setClass(getActivity(), CanteenActivity.class);
        startActivity(intent);
    }

    public Activity getActivity() {
        return activity;
    }

    public void startActivity(Intent intent) {
        this.activity.startActivity(intent);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView searchPic;
        private TextView searchCompany;
        private RatingBar searchRating;
        private TextView companyInfo;
        private ImageView divider;
        private ListView searchDishesList;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            searchPic = (ImageView) view.findViewById(R.id.searchPic);
            searchCompany = (TextView) view.findViewById(R.id.searchCompany);
            searchRating = (RatingBar) view.findViewById(R.id.searchRating);
            companyInfo = (TextView) view.findViewById(R.id.companyInfo);
            divider = (ImageView) view.findViewById(R.id.divider);
            searchDishesList = (ListView) view.findViewById(R.id.searchDishesList);
        }
    }

}

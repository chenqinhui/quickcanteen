package com.quickcanteen.quickcanteen.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.bean.CompanyInfoBean;
import com.quickcanteen.quickcanteen.bean.UserCommentBean;
import com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader;

import java.util.List;

import static com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader.asyncBitmapLoader;

/**
 * Created by Cynthia on 2017/7/11.
 */
public class CanteenListAdapter extends RecyclerView.Adapter<CanteenListAdapter.ViewHolder> implements View.OnClickListener{
    private List<CompanyInfoBean> mCanteenList;
    private Activity activity;
    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.asyncBitmapLoader;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView canteenImg;
        TextView canteenName;
        RatingBar canteenRating;
        TextView busyDegree;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            canteenImg = (ImageView)view.findViewById(R.id.canteenImg);
            canteenName = (TextView)view.findViewById(R.id.canteenName);
            canteenRating = (RatingBar)view.findViewById(R.id.canteenRating);
            busyDegree = (TextView)view.findViewById(R.id.busyDegree);
        }
    }

    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取position
        skip(mCanteenList.get((int)v.getTag()));
    }

    public CanteenListAdapter(List<CompanyInfoBean> canteenList, Activity activity){
        mCanteenList = canteenList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.canteen_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompanyInfoBean companyInfoItem = mCanteenList.get(position);
        holder.canteenName.setText(companyInfoItem.getCompanyName());
        holder.canteenRating.setRating(companyInfoItem.getRating().floatValue());
        holder.itemView.setTag(position);

        Bitmap bitmap = asyncBitmapLoader.loadBitmap(holder.canteenImg, companyInfoItem.getCompanyPortrait(), holder.canteenImg.getLayoutParams().width, holder.canteenImg.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            }
        });
        if(bitmap!=null){
            holder.canteenImg.setImageBitmap(bitmap);
        }
        holder.itemView.setTag(position);
        /*
        * busyDegree指食堂当前的拥挤程度
        * 1：空闲  2：适中  3：拥挤
        * */
        switch (companyInfoItem.getBusyDegree()){
            case 1:
                holder.busyDegree.setText("空闲");
                holder.busyDegree.setTextColor(R.color.color_busyDegree_free);
                break;
            case 2:
                holder.busyDegree.setText("适中");
                holder.busyDegree.setTextColor(R.color.color_busyDegree_medium);
                break;
            case 3:
                holder.busyDegree.setText("拥挤");
                holder.busyDegree.setTextColor(R.color.color_busyDegree_crowed);
                break;
            default:
                holder.busyDegree.setText("");
                holder.busyDegree.setTextColor(R.color.color_busyDegree_crowed);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCanteenList.size();
    }

    public void skip(CompanyInfoBean companyInfoBean){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("companyId",companyInfoBean.getCompanyId());
        bundle.putSerializable("companyName",companyInfoBean.getCompanyName());
        intent.putExtras(bundle);
        intent.setClass(activity, CanteenActivity.class);
        startActivity(intent);
    }

    public void startActivity(Intent intent) {
        this.activity.startActivity(intent);
    }

}

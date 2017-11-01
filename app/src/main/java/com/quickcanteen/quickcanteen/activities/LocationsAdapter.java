package com.quickcanteen.quickcanteen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.order.CompleteActivity;
import com.quickcanteen.quickcanteen.activities.order.OrderActivity;
import com.quickcanteen.quickcanteen.activities.order.SuccessActivity;
import com.quickcanteen.quickcanteen.bean.LocationBean;
import com.quickcanteen.quickcanteen.bean.OrderBean;

import java.util.List;

/**
 * Created by 11022 on 2017/7/1.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> implements View.OnClickListener  {
    private List<LocationBean> locationBeans;
    private Activity activity;
    private OnItemClickListener mOnItemClickListener = null;

    public LocationsAdapter(List<LocationBean> locationBeans, Activity activity) {
        this.locationBeans = locationBeans;
        this.activity = activity;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, LocationBean locationBean);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, locationBeans.get((int) v.getTag()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LocationBean locationBean = locationBeans.get(position);
        holder.view.setTag(position);
        holder.address.setText(locationBean.getAddress());
    }

    @Override
    public int getItemCount() {
        return locationBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            address = (TextView) view.findViewById(R.id.address);
        }
    }
}


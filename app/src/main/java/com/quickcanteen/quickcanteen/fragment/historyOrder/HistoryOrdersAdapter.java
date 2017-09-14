package com.quickcanteen.quickcanteen.fragment.historyOrder;

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
import com.quickcanteen.quickcanteen.activities.CommentActivity;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.order.CompleteActivity;
import com.quickcanteen.quickcanteen.activities.order.OrderActivity;
import com.quickcanteen.quickcanteen.activities.order.SuccessActivity;
import com.quickcanteen.quickcanteen.bean.OrderBean;

import java.util.List;

/**
 * Created by 11022 on 2017/7/1.
 */

public class HistoryOrdersAdapter extends RecyclerView.Adapter<HistoryOrdersAdapter.ViewHolder> implements View.OnClickListener {
    private List<OrderBean> orderBeans;
    private Activity activity;

    public HistoryOrdersAdapter(List<OrderBean> orderBeans, Activity activity) {
        this.orderBeans = orderBeans;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取position
        skip(orderBeans.get((int)v.getTag()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_order_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderBean orderBean = orderBeans.get(position);
        holder.view.setTag(position);
        holder.orderCompany.setText(orderBean.getCompanyName());
        holder.orderState.setText(orderBean.getOrderStatus().getDesc());
        holder.orderPrice.setText(orderBean.getTotalPrice().toString());
        if (orderBean.getDishesBeanList().size() == 1) {
            holder.orderName.setText(orderBean.getDishesBeanList().get(0).getDishesName());
        } else {
            holder.orderName.setText(orderBean.getDishesBeanList().get(0).getDishesName() + "等");
        }
        holder.assessOrder.setTag(position);
        holder.addOrder.setTag(position);
        switch (orderBean.getOrderStatus()) {
            case COMPLETE:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setText("再来一单");
                holder.addOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toAddCart(orderBean);
                    }
                });
                break;
            case NOT_COMMENT:
                holder.assessOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setText("再来一单");
                holder.assessOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toAssess(orderBean);
                    }
                });
                holder.addOrder.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        toAddCart(orderBean);
                    }
                });
                break;
            case NOT_PAID:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setText("去付款");
                holder.addOrder.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        skip(orderBean);
                    }
                });
                break;
            case PEND_TO_TAKE:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setText("去取餐");
                holder.addOrder.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        skip(orderBean);
                    }
                });
                break;
            case CANCELLED:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.GONE);
                break;
            case DISTRIBUTING:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.GONE);
                break;
            case CLOSED:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.VISIBLE);
                holder.addOrder.setText("再来一单");
                holder.addOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toAddCart(orderBean);
                    }
                });
                break;
            case NEW:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.GONE);
                break;
            case PREPARING:
                holder.assessOrder.setVisibility(View.GONE);
                holder.addOrder.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView orderImg;
        private TextView orderCompany;
        private TextView orderState;
        private ImageView line;
        private TextView orderName;
        private TextView orderPrice;
        private Button assessOrder;
        private Button addOrder;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            orderImg = (ImageView) view.findViewById(R.id.orderImg);
            orderCompany = (TextView) view.findViewById(R.id.orderCompany);
            orderState = (TextView) view.findViewById(R.id.orderState);
            line = (ImageView) view.findViewById(R.id.line);
            orderName = (TextView) view.findViewById(R.id.orderName);
            orderPrice = (TextView) view.findViewById(R.id.orderPrice);
            assessOrder = (Button) view.findViewById(R.id.assessOrder);
            addOrder = (Button) view.findViewById(R.id.addOrder);
        }
    }

    public void toAssess(OrderBean orderBean) {
        String str_company = orderBean.getCompanyName();
        Intent intent = new Intent();
        intent.setClass(getActivity(), CommentActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("orderBean", orderBean);
        bundle.putString("company_name", str_company);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void skip(OrderBean orderBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderBean", orderBean);
        intent.putExtras(bundle);
        switch (orderBean.getOrderStatus()) {
            case NOT_PAID:
                intent.setClass(getActivity(), OrderActivity.class);
                break;
            case PEND_TO_TAKE:
                intent.setClass(getActivity(), SuccessActivity.class);
                break;
            default:
                intent.setClass(getActivity(), CompleteActivity.class);
                break;
        }
        //Intent it = new Intent(HistoryOrderFragment.this, CompleteActivity.class);
        startActivity(intent);
    }

    /*
    **再来一单，跳转到挑选商品界面，将订单添加至购物车
     */
    public void toAddCart(OrderBean orderBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderBean", orderBean);
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
}


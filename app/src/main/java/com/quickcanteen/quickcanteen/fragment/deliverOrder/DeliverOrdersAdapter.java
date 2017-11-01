package com.quickcanteen.quickcanteen.fragment.deliverOrder;

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
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.activities.CommentActivity;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.order.CompleteActivity;
import com.quickcanteen.quickcanteen.activities.order.OrderActivity;
import com.quickcanteen.quickcanteen.activities.order.SuccessActivity;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11022 on 2017/7/1.
 */

public class DeliverOrdersAdapter extends RecyclerView.Adapter<DeliverOrdersAdapter.ViewHolder> implements View.OnClickListener {
    private List<OrderBean> orderBeans;
    private Activity activity;
    private IOrderAction orderAction;

    public DeliverOrdersAdapter(List<OrderBean> orderBeans, Activity activity) {
        this.orderBeans = orderBeans;
        this.activity = activity;
        orderAction = new OrderActionImpl(activity);
    }

    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取position
        skip(orderBeans.get((int) v.getTag()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliver_order_list, parent, false);
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
        holder.orderUserInfo.setText(orderBean.getUserRealName()+" "+orderBean.getUserTelephone());
        holder.deliverPrice.setText(orderBean.getDeliverPrice().toString());
        holder.orderAddress.setText(orderBean.getLocationBean().getAddress());
        switch (orderBean.getOrderStatus()){
            case DISTRIBUTING:
                holder.takeOrder.setVisibility(View.VISIBLE);
                holder.takeOrder.setText("完成配送");
                holder.takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new CompleteOrderThread(orderBean.getOrderId())).start();
                    }
                });
                break;
            case PEND_TO_DISTRIBUTE:
                holder.takeOrder.setVisibility(View.VISIBLE);
                holder.takeOrder.setText("我要接单");
                holder.takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new AcceptOrderThread(orderBean.getOrderId())).start();
                    }
                });
                break;
            case NOT_COMMENT:
            case COMPLETE:
                holder.takeOrder.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    public class AcceptOrderThread implements Runnable {
        private int orderId;

        public AcceptOrderThread(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            try {
                final BaseJson baseJson = orderAction.askForDeliverOrder(orderId);
                if (!baseJson.getReturnCode().contains("E")) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "接单成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, baseJson.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    public class CompleteOrderThread implements Runnable {
        private int orderId;

        public CompleteOrderThread(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            try {
                final BaseJson baseJson = orderAction.completeDeliverOrder(orderId);
                if (!baseJson.getReturnCode().contains("E")) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "成功完成配送", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, baseJson.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

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
        private TextView orderAddress;
        private TextView orderUserInfo;
        private TextView deliverPrice;
        private Button takeOrder;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            orderImg = (ImageView) view.findViewById(R.id.orderImg);
            orderCompany = (TextView) view.findViewById(R.id.orderCompany);
            orderState = (TextView) view.findViewById(R.id.orderState);
            line = (ImageView) view.findViewById(R.id.line);
            orderAddress = (TextView) view.findViewById(R.id.orderAddress);
            orderUserInfo = (TextView) view.findViewById(R.id.orderUserInfo);
            deliverPrice = (TextView) view.findViewById(R.id.deliverPrice);
            takeOrder = (Button) view.findViewById(R.id.takeOrder);
        }
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

    public Activity getActivity() {
        return activity;
    }

    public void startActivity(Intent intent) {
        this.activity.startActivity(intent);
    }
}


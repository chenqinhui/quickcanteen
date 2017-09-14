package com.quickcanteen.quickcanteen.fragment.historyOrder;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderFragment extends android.support.v4.app.Fragment {
    private static Handler handler = new Handler();
    private String message;
    private int pageNumber = 0;
    private int pageSize = 5;
    private int lastVisibleItem = 5;
    /**
     * Called when the activity is first created.
     */

    String tag = "HistoryOrderFragment.class";

    //private OrderState orderState = OrderState.ALL;

    private RecyclerView historyOrdersList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar orderListToolbar;

    private LinearLayoutManager linearLayoutManager;
    private HistoryOrdersAdapter adapter;

    private IOrderAction orderAction = new OrderActionImpl(getActivity());

    private List<OrderBean> orderBeans = new ArrayList<>();

    //widget for pull to refresh
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history_order, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderAction = new OrderActionImpl(getActivity());
        //BaseActivity.initializeButtom(getActivity());
        orderListToolbar = (Toolbar) getActivity().findViewById(R.id.orderListToolbar);
        orderListToolbar.setTitle("全部订单");
        ((AppCompatActivity) getActivity()).setSupportActionBar(orderListToolbar);
        orderListToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        orderListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            View edit = getActivity().findViewById(R.id.action_edit);

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        Toast.makeText(getActivity(), "编辑订单", 0).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 0;
                orderBeans.clear();
                new Thread(new MyThread()).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Bundle bundle = getArguments();
        //orderState = OrderState.valueOf(bundle.getString("OrderState", "ALL"));

        historyOrdersList = (RecyclerView) getActivity().findViewById(R.id.historyOrderList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        historyOrdersList.setLayoutManager(linearLayoutManager);
        new Thread(new MyThread()).start();
        adapter = new HistoryOrdersAdapter(orderBeans, getActivity());
        historyOrdersList.setAdapter(adapter);
        historyOrdersList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    new Thread(new MyThread()).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.order_list_top_toolbar, menu);
        return true;
    }*/


    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = orderAction.getOrdersListByUserIDByPage(pageNumber, pageSize);
                List<OrderBean> newOrdersList = new ArrayList<>();
                JSONArray jsonArray = baseJson.getJSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                    newOrdersList.add(new OrderBean(tempJsonObject));
                }
                pageNumber++;
                orderBeans.addAll(newOrdersList);

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
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    public static HistoryOrderFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString("OrderState", "ALL");
        HistoryOrderFragment historyOrder = new HistoryOrderFragment();
        historyOrder.setArguments(bundle);
        return historyOrder;
    }

}
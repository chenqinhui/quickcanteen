package com.quickcanteen.quickcanteen.fragment.deliverOrder;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.fragment.historyOrder.HistoryOrderFragment;
import com.quickcanteen.quickcanteen.fragment.historyOrder.HistoryOrdersAdapter;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverOrderFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static Handler handler = new Handler();
    private String message;
    private int pageNumber = 0;
    private int pageSize = 5;
    private int lastVisibleItem = 5;
    private IOrderAction orderAction = new OrderActionImpl(getActivity());

    private List<OrderBean> orderBeans = new ArrayList<>();

    private RecyclerView deliverOrdersList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private DeliverOrdersAdapter adapter;

    private int opt;

    public DeliverOrderFragment() {
        // Required empty public constructor
    }

    public static DeliverOrderFragment newInstance(Integer opt) {
        DeliverOrderFragment fragment = new DeliverOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, opt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opt = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = opt == 0 ? orderAction.getNeedDeliverOrdersByPage(pageNumber, pageSize) : orderAction.getDeliverOrdersByPage(pageNumber, pageSize);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliver_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAction = new OrderActionImpl(getActivity());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
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
        //orderState = OrderState.valueOf(bundle.getString("OrderState", "ALL"));

        deliverOrdersList = (RecyclerView) view.findViewById(R.id.deliverOrderList);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        deliverOrdersList.setLayoutManager(linearLayoutManager);
        new Thread(new MyThread()).start();
        adapter = new DeliverOrdersAdapter(orderBeans, getActivity());
        deliverOrdersList.setAdapter(adapter);
        deliverOrdersList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
}

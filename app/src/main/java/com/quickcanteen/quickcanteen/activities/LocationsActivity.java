package com.quickcanteen.quickcanteen.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.location.ILocationAction;
import com.quickcanteen.quickcanteen.actions.location.impl.LocationActionImpl;
import com.quickcanteen.quickcanteen.bean.LocationBean;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.fragment.historyOrder.HistoryOrderFragment;
import com.quickcanteen.quickcanteen.fragment.historyOrder.HistoryOrdersAdapter;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends BaseActivity {
    private RecyclerView locationList;
    private LinearLayoutManager linearLayoutManager;
    private LocationsAdapter adapter;
    private List<LocationBean> locationBeanList = new ArrayList<>();
    private ILocationAction locationAction = new LocationActionImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        initializeTop(this, true, "收货地址");
        locationList = (RecyclerView) findViewById(R.id.locations);

        linearLayoutManager = new LinearLayoutManager(this);
        locationList.setLayoutManager(linearLayoutManager);
        new Thread(new MyThread()).start();
        adapter = new LocationsAdapter(locationBeanList, this);
        locationList.setAdapter(adapter);
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = locationAction.getCurrentUserLocations();
                List<LocationBean> newLocationBeansList = new ArrayList<>();
                JSONArray jsonArray = baseJson.getJSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                    newLocationBeansList.add(new LocationBean(tempJsonObject));
                }
                locationBeanList.addAll(newLocationBeansList);

            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LocationsActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}

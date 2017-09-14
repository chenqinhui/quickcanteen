package com.quickcanteen.quickcanteen.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderActivity extends BaseActivity {

    private Date orderTime;
    private String orderTimeContent;
    private NumberFormat numberFormat;
    private int totalQuantity = 0;
    private double totalPrice = 0;
    private ListView dishesView;
    private TextView totalQuantityTextView, totalPriceTextView, companyNameTextView;
    private static Handler handler = new Handler();
    private int companyID;
    private String message, companyName;
    private ArrayList<GoodsItem> list;
    private int ordersID;
    private String selected = "取餐";
    private RadioGroup typeGroup;
    private RadioButton checkedType;
    private TextView timeSlotTitle;
    private RadioGroup timeSlotGroup;
    private String[] timeSlot = {"16:30之前", "16:30~16:35", "16:35~16:40", "16:40~16:45", "16:45~16:50", "16:50之后"};
    private IOrderAction orderAction = new OrderActionImpl(this);

    private OrderBean orderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        BaseActivity.initializeTop(this, true, "确认订单");

        numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setMaximumFractionDigits(2);
        dishesView = (ListView) findViewById(R.id.dishesView);
        totalQuantityTextView = (TextView) findViewById(R.id.totalQuantity);
        totalPriceTextView = (TextView) findViewById(R.id.totalPrice);
        companyNameTextView = (TextView) findViewById(R.id.companyName);

        /*choose take meal type*/
        typeGroup = (RadioGroup) findViewById(R.id.typeGroup);
        timeSlotTitle = (TextView) findViewById(R.id.timeSlotTitle);
        timeSlotGroup = (RadioGroup) findViewById(R.id.TimeSlotGroup);

        int timeSlotCount = timeSlot.length;
        for (int i = 0; i < timeSlotCount; i++) {
            RadioButton timeSlotEach = new RadioButton(this);
            timeSlotEach.setText(timeSlot[i]);
            timeSlotGroup.addView(timeSlotEach);
        }

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //点击事件获取的选择对象
                checkedType = (RadioButton) typeGroup.findViewById(checkedId);
                if (checkedId == R.id.invite) {
                    timeSlotTitle.setText("选择到窗取餐的时间：");
                    timeSlotTitle.setVisibility(View.VISIBLE);
                    timeSlotGroup.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.distribution) {
                    timeSlotTitle.setText("大约会在20分钟后送达");
                    timeSlotTitle.setVisibility(View.VISIBLE);
                    timeSlotGroup.setVisibility(View.GONE);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        orderBean = (OrderBean) bundle.getSerializable("orderBean");
        companyID = orderBean.getCompanyId();
        ordersID = orderBean.getOrderId();
        companyName= orderBean.getCompanyName();
        list = GoodsItem.getGoodsItemList(orderBean.getDishesBeanList());

        companyNameTextView.setText(companyName);

        ArrayList<HashMap<String, Object>> arrayList = getData(list);

        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.dishes_view_content,
                new String[]{"name", "quantity", "price"},
                new int[]{R.id.dishName, R.id.dishQuantity, R.id.dishPrice});
        dishesView.setAdapter(adapter);

        totalQuantityTextView.setText(String.valueOf(totalQuantity));
        totalPriceTextView.setText(numberFormat.format(totalPrice));


        Button button_to_pay = (Button) findViewById(R.id.payButton);
        button_to_pay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new PayThread()).start();
            }
        });
    }


    class PayThread implements Runnable {
        @Override
        public void run() {
            message = "支付成功";
            try {
                BaseJson baseJson = orderAction.pay(ordersID, selected);
                int result = baseJson.getSingleIntegerResult();
                switch (result) {
                    case 0:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderBean", orderBean);
                        intent.putExtras(bundle);
                        intent.setClass(OrderActivity.this, SuccessActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        message = baseJson.getErrorMessage();
                        break;
                }
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OrderActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private ArrayList<HashMap<String, Object>> getData(ArrayList<GoodsItem> list) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

        for (GoodsItem temp : list) {
            arrayList.add(getElement(temp));
        }

        return arrayList;
    }

    private HashMap<String, Object> getElement(GoodsItem item) {
        HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
        tempHashMap.put("name", item.name);
        tempHashMap.put("quantity", item.count);
        totalQuantity += item.count;
        tempHashMap.put("price", numberFormat.format(item.price));
        totalPrice += item.price * item.count;
        return tempHashMap;
    }

}

package com.quickcanteen.quickcanteen.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.CommentActivity;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.bean.OrderBean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CompleteActivity extends BaseActivity {

    private NumberFormat numberFormat;
    private int totalQuantity = 0;
    private double totalPrice = 0;
    private ListView dishesView;
    private TextView totalQuantityTextView, totalPriceTextView, companyNameTextView, orderTimeTextView, pickTimeTextView;

    private static Handler handler = new Handler();
    private String message;
    private OrderBean orders;
    private IOrderAction orderAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setMaximumFractionDigits(2);
        dishesView = (ListView) findViewById(R.id.dishesView);
        totalQuantityTextView = (TextView) findViewById(R.id.totalQuantity);
        totalPriceTextView = (TextView) findViewById(R.id.totalPrice);
        companyNameTextView = (TextView) findViewById(R.id.companyName);
        orderTimeTextView = (TextView) findViewById(R.id.orderTime);
        pickTimeTextView = (TextView) findViewById(R.id.pickTime);

        Bundle bundle = this.getIntent().getExtras();
        orders = (OrderBean) bundle.getSerializable("orderBean");
        companyNameTextView.setText(orders.getCompanyName());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderTimeTextView.setText(sdf.format(orders.getPublishTime()).toString());
        pickTimeTextView.setText(sdf.format(orders.getCompleteTime()).toString());

        ListView dishesView = (ListView) findViewById(R.id.dishesView);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(orders.getDishesBeanList()), R.layout.dishes_view_content,
                new String[]{"name", "quantity", "price"},
                new int[]{R.id.dishName, R.id.dishQuantity, R.id.dishPrice});
        dishesView.setAdapter(adapter);

        totalQuantityTextView.setText(String.valueOf(totalQuantity));
        totalPriceTextView.setText(numberFormat.format(totalPrice));

        BaseActivity.initializeTop(this, true, "确认取餐");

        Button button_to_comment = (Button) findViewById(R.id.commentButton);
        button_to_comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new CommentThread()).start();
            }
        });

        Button button_to_order = (Button) findViewById(R.id.moreButton);
        button_to_order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toAddCart();
            }
        });
    }


    class CommentThread implements Runnable {
        @Override
        public void run() {
            message = "开始评价";
            try {
                Intent intent = new Intent(CompleteActivity.this, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("OrderBean", orders);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CompleteActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private ArrayList<HashMap<String, Object>> getData(ArrayList<DishesBean> list) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (GoodsItem temp : GoodsItem.getGoodsItemList(list)) {
            arrayList.add(getElement(temp));
        }
        return arrayList;
    }

    private HashMap<String, Object> getElement(GoodsItem goodsItem) {
        HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
        tempHashMap.put("name", goodsItem.name);
        tempHashMap.put("quantity", goodsItem.count);
        totalQuantity += goodsItem.count;
        tempHashMap.put("price", numberFormat.format(goodsItem.price));
        totalPrice += goodsItem.price * goodsItem.count;
        return tempHashMap;
    }

    public void toAddCart() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderBean", orders);
        bundle.putInt("companyId",orders.getCompanyId());
        bundle.putSerializable("companyName",orders.getCompanyName());
        intent.putExtras(bundle);
        intent.setClass(this, CanteenActivity.class);
        startActivity(intent);
    }

}

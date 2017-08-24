package com.quickcanteen.quickcanteen.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;

public class SuccessActivity extends BaseActivity {

    private static Handler handler = new Handler();
    private int ordersID;

    private String message;
    private int companyID;
    private TextView deadLine;
    private String time;
    private TextView orderIdText;
    private OrderBean orderBean;

    private IOrderAction orderAction = new OrderActionImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        BaseActivity.initializeTop(this, true, "支付成功");
        Bundle bundle = this.getIntent().getExtras();
        orderBean = (OrderBean) bundle.getSerializable("orderBean");
        companyID = orderBean.getCompanyId();
        ordersID = orderBean.getOrderId();
        deadLine = (TextView) findViewById(R.id.deadline);
        orderIdText = (TextView) findViewById(R.id.orderId);
        orderIdText.setText(String.valueOf(ordersID));
        Button button_to_cancel = (Button) findViewById(R.id.cancelButton);
        button_to_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new CancelThread()).start();
            }
        });
        Button button_to_complete = (Button) findViewById(R.id.completeButton);
        button_to_complete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new ConfirmThread()).start();

            }
        });
        new Thread(new MyThread()).start();
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                //companyName = orderService.getCompanyInfoByID(companyID).getRealName();
                BaseJson baseJson = orderAction.getTimeSlotByOrdersID(ordersID);
                time = baseJson.getSingleStringResult();
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    deadLine.setText(time);
                }
            });
        }
    }

    class CancelThread implements Runnable {
        @Override
        public void run() {
            message = "取消成功";
            try {
                orderAction.unsubscribe(ordersID);
                Intent intent = new Intent();
                intent.setClass(SuccessActivity.this, CancelActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SuccessActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ConfirmThread implements Runnable {
        @Override
        public void run() {
            message = "确认成功";
            try {
                orderAction.takeMeal(ordersID);
                Intent intent = new Intent(SuccessActivity.this, CompleteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderBean", orderBean);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SuccessActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

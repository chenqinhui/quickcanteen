package com.quickcanteen.quickcanteen.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.main.MainActivity;


public class CancelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        BaseActivity.initializeTop(this, true, "退订");
        Button button_to_submit=(Button)findViewById(R.id.submitButton);
        button_to_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CancelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

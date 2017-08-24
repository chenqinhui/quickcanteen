package com.quickcanteen.quickcanteen.activities.initial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.main.MainActivity;
import com.quickcanteen.quickcanteen.activities.login.LoginActivity;
import com.quickcanteen.quickcanteen.activities.register.RegisterActivity;

public class InitialActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        initView();
    }

    @Override
    protected void initView() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", 0);
        if (userID != 0) {
            Intent intent = new Intent();
            intent.setClass(InitialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        ImageButton buttonToLogin = (ImageButton) findViewById(R.id.initial_login_jump);
        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InitialActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageButton buttonToRegister = (ImageButton) findViewById(R.id.initial_register_jump);
        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InitialActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

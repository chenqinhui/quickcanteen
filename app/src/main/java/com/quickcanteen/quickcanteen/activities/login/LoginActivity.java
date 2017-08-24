package com.quickcanteen.quickcanteen.activities.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.user.IUserAction;
import com.quickcanteen.quickcanteen.actions.user.impl.UserActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.main.MainActivity;
import com.quickcanteen.quickcanteen.utils.BaseJson;

public class LoginActivity extends BaseActivity {
    private EditText accountNumber, userPassword;
    private Button loginButton;

    private SharedPreferences sharedPreferences;

    private static Handler handler = new Handler();
    private String message;

    private IUserAction userAction = new UserActionImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBarTitleText("登录");
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        accountNumber = (EditText) findViewById(R.id.login_telephone);
        userPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.user_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new LoginThread()).start();
            }
        });
    }

    public class LoginThread implements Runnable {
        @Override
        public void run() {
            message = "登录成功";
            try {
                String accountNumberString = accountNumber.getText().toString();
                String userPasswordString = userPassword.getText().toString();
                BaseJson baseJson = userAction.login(accountNumberString, userPasswordString);
                Log.d("ReturnCode", baseJson.getReturnCode());
                switch (baseJson.getReturnCode()) {
                    case "2.0":
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        String[] s=baseJson.getSingleStringResult().split(" ");
                        editor.putInt("userID", Integer.parseInt(s[1]));
                        editor.putString("X-TOKEN",s[0].trim());
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
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
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

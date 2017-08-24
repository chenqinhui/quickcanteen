package com.quickcanteen.quickcanteen.activities.register;

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

public class RegisterActivity extends BaseActivity {
    private EditText userName;
    private EditText accountNumber;
    private EditText teleNum;
    private EditText userPassword;
    private EditText userPasswordAgain;
    private EditText validateCode;
    private Button registerButton;

    private SharedPreferences sharedPreferences;

    private static Handler handler = new Handler();
    private String message;

    private IUserAction userAction = new UserActionImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = (EditText) findViewById(R.id.registerName);
        accountNumber = (EditText) findViewById(R.id.registerID);
        teleNum = (EditText) findViewById(R.id.registerTelephone);
        userPassword = (EditText) findViewById(R.id.registerPassword);
        userPasswordAgain = (EditText) findViewById(R.id.registerPasswordAgain);
        validateCode = (EditText) findViewById(R.id.registerIdentify);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new RegisterThread()).start();
            }
        });
    }

    public class RegisterThread implements Runnable {
        @Override
        public void run() {
            message = "注册成功";
            try {
                String accountNumberString = accountNumber.getText().toString();
                String userPasswordString = userPassword.getText().toString();
                String userNameString = userName.getText().toString();
                String teleNumString = teleNum.getText().toString();
                String userPasswordAgainString = userPasswordAgain.getText().toString();
                String validateCodeString = validateCode.getText().toString();
                if (accountNumberString.length() == 0 || userPasswordString.length() == 0 || userPasswordAgainString.length() == 0 || teleNumString.length() == 0 || userNameString.length() == 0) {
                    Log.d("ReturnCode", "1.0.E.2");
                    message = "用户信息不能为空";
                } else if (!userPasswordAgainString.equals(userPasswordString)) {
                    Log.d("ReturnCode", "1.0.E.3");
                    message = "两次密码不相同";
                } else if (userPasswordString.length() <= 6 && userPasswordString.length() >= 18) {
                    message = "密码的长度必须在6-18位";
                } else if (userAction.validateRealName(accountNumberString, userNameString) != 0) {
                    Log.d("ReturnCode", "1.0.E.1");
                    message = "姓名和学号不匹配";
                } else if (userAction.validateTeleNum(teleNumString, validateCodeString) != 0) {
                    Log.d("ReturnCode", "1.0.E.5");
                    message = "验证码错误";
                } else {
                    BaseJson baseJson = userAction.register(accountNumberString, userPasswordString, userNameString, teleNumString);
                    Log.d("ReturnCode", baseJson.getReturnCode());
                    switch (baseJson.getReturnCode()) {
                        case "2.0":
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putInt("userID", baseJson.getSingleIntegerResult());
                            editor.commit();
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        default:
                            message = baseJson.getErrorMessage();
                            break;
                    }
                }
            } catch (Exception e) {
                message = "连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

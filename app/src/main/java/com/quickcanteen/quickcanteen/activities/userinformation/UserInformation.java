package com.quickcanteen.quickcanteen.activities.userinformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.user.IUserAction;
import com.quickcanteen.quickcanteen.actions.user.impl.UserActionImpl;
import com.quickcanteen.quickcanteen.activities.BaseActivity;
import com.quickcanteen.quickcanteen.activities.LocationsActivity;
import com.quickcanteen.quickcanteen.activities.initial.InitialActivity;
import com.quickcanteen.quickcanteen.bean.UserInfoBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;

public class UserInformation extends BaseActivity {

    private TextView realName, collegeName, accountNumber, teleNum;
    private Button exit, editpwd, signUpForDeliver, location;
    private ProgressBar userInfoProgressBar;
    private UserInfoBean userInfo;
    private IUserAction userAction = new UserActionImpl(this);
    private static Handler handler = new Handler();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        BaseActivity.initializeTop(this, true, "个人信息");
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        realName = (TextView) findViewById(R.id.userName);
        collegeName = (TextView) findViewById(R.id.userMajor);
        accountNumber = (TextView) findViewById(R.id.userId);
        teleNum = (TextView) findViewById(R.id.userTel);
        exit = (Button) findViewById(R.id.exit);
        editpwd = (Button) findViewById(R.id.editPwd);
        signUpForDeliver = (Button) findViewById(R.id.signUpForDeliver);
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                //bundle.putString("oldPassword", userInfo.getUserPassword());
                intent.putExtras(bundle);
                intent.setClass(UserInformation.this, LocationsActivity.class);
                startActivity(intent);
            }
        });
        signUpForDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new SignUpForDeliverThread()).start();
            }
        });
        editpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                //bundle.putString("oldPassword", userInfo.getUserPassword());
                intent.putExtras(bundle);
                intent.setClass(UserInformation.this, EditPassword.class);
                startActivity(intent);
            }
        });
        teleNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("infoType", "telephone");
                intent.setClass(UserInformation.this, EditUserInfo.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                Intent intent = new Intent();
                intent.setClass(UserInformation.this, InitialActivity.class);
                finish();
                startActivity(intent);
            }
        });

        userInfoProgressBar = (ProgressBar) findViewById(R.id.userInfoProgressBar);
        userInfoProgressBar.setIndeterminate(false);
        userInfoProgressBar.setVisibility(View.VISIBLE);
        new Thread(new MyThread()).start();
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = userAction.getCurrentUserInfo();
                Log.d("ReturnCode", baseJson.getReturnCode());
                userInfo = new UserInfoBean(baseJson.getJSONObject());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(UserInformation.this, "123", Toast.LENGTH_SHORT).show();
                        realName.setText(userInfo.getRealName());
                        collegeName.setText(userInfo.getCollegeName());
                        accountNumber.setText(userInfo.getAccountNumber());
                        teleNum.setText(userInfo.getTelephone());
                    }
                });
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserInformation.this, "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
                userInfoProgressBar.setVisibility(View.GONE);
            }

        }
    }

    public class SignUpForDeliverThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = userAction.signUpForDeliver();
                Log.d("ReturnCode", baseJson.getReturnCode());
                userInfo = new UserInfoBean(baseJson.getJSONObject());
                if (userInfo.getDeliver()) {
                    if (baseJson.getReturnCode().contains("E")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserInformation.this, "你已经申请过了", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserInformation.this, "申请成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserInformation.this, "申请失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserInformation.this, "连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
}

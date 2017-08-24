package com.quickcanteen.quickcanteen.activities.userinformation;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.BaseActivity;

public class EditPassword extends BaseActivity {

    private TextView oldPwd,newPwd,againPwd;
    private Button confirmEdit;
    private Handler handler=new Handler();
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        BaseActivity.initializeTop(this, true, "修改密码");

        oldPwd=(TextView)findViewById(R.id.oldPwd);
        newPwd=(TextView)findViewById(R.id.newPwd);
        againPwd=(TextView)findViewById(R.id.againPwd);
        confirmEdit=(Button)findViewById(R.id.confirmEdit);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswordString=oldPwd.getText().toString();
                String newPasswordString=newPwd.getText().toString();
                String againPasswordString =againPwd.getText().toString();
                if(oldPasswordString.length()==0||newPasswordString.length()==0||againPasswordString.length()==0){
                    Toast.makeText(EditPassword.this, "请填写信息", Toast.LENGTH_SHORT).show();
                }else if(!newPasswordString.equals(againPasswordString)){
                    Toast.makeText(EditPassword.this, "两遍密码不一致", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
    }
    class ChangePasswordThread implements Runnable{
        private String newPasswordString;

        public ChangePasswordThread(String newPasswordString) {
            this.newPasswordString = newPasswordString;
        }

        @Override
        public void run() {
            message="修改成功";
            try{
                //userInformationService.changePassword(newPasswordString);
            }catch (Exception e){
                message="连接错误";
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EditPassword.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

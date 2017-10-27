package com.quickcanteen.quickcanteen.activities.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initView();
        initializeTop(this,true,"设置");
    }
}

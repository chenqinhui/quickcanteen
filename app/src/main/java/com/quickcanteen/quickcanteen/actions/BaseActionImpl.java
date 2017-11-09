package com.quickcanteen.quickcanteen.actions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import com.quickcanteen.quickcanteen.utils.HttpUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 11022 on 2017/5/14.
 */
public class BaseActionImpl implements IBaseAction {
    protected static final String path = "http://101.132.108.158:10000/api/";
    //protected static final String path = "http://10.0.3.2:8080/api/";
    protected Activity activity;
    protected BaseJson baseJson;

    public BaseActionImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCurrentUserID() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userID", 0);
    }

    public String httpConnectByPost(String Url, Map<String, String> para) throws IOException {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("X-TOKEN", "");
        return HttpUtils.httpConnectByPost(path + Url, para, token);
    }
}

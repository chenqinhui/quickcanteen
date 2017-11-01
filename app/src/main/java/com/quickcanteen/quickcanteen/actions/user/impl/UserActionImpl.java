package com.quickcanteen.quickcanteen.actions.user.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.user.IUserAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11022 on 2017/6/29.
 */
public class UserActionImpl extends BaseActionImpl implements IUserAction {
    public UserActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson editPassword(int userID, String oldPassword, String newPassword) throws IOException, JSONException {
        Map<String,String> map = new HashMap<>();
        map.put("userID",String.valueOf(userID));
        map.put("userPassword",oldPassword);
        map.put("newPassword",newPassword);
        String result = httpConnectByPost("user/editPassword",map);
        return new BaseJson(result);
    }


    @Override
    public BaseJson editUserInfo(String password, int userID,String infoType, String correctInfo) throws IOException, JSONException {
        Map<String,String> map = new HashMap<>();
        map.put("userID",String.valueOf(userID));
        map.put("userPassword",password);
        map.put("infoType",infoType);
        map.put("correctInfo",correctInfo);
        String result = httpConnectByPost("user/editUserInfo",map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson login(String accountNumber, String userPassword) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("accountNumber", accountNumber);
        map.put("userPassword", userPassword);
        String result = httpConnectByPost("user/login", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson register(String accountNumber, String userPassword, String realName, String telephone) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("accountNumber", accountNumber);
        map.put("userPassword", userPassword);
        map.put("telephone", telephone);
        map.put("realName",realName);
        String result = httpConnectByPost("user/register", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getCurrentUserInfo() throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("user/getUserInfoByUserID", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson signUpForDeliver() throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("user/signUpForDeliver", map);
        return new BaseJson(result);
    }

    @Override
    public int validateRealName(String accountNumber, String realName) throws IOException {
        return 0;
    }

    @Override
    public int validateTeleNum(String teleNum, String validateNumber) throws IOException {
        return 0;
    }
}

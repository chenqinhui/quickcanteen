package com.quickcanteen.quickcanteen.actions.main.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.main.IMainAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import com.quickcanteen.quickcanteen.utils.HttpUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cynthia on 2017/7/11.
 */
public class MainActionImpl extends BaseActionImpl implements IMainAction {
    public MainActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson getCompanyInfoByPage(int pageNumber, int pageSize) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("pageNumber",String.valueOf(pageNumber));
        map.put("pageSize",String.valueOf(pageSize));
        String result = httpConnectByPost("main/getCompanyInfoByPage", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getRecommendListByUserId() throws IOException,JSONException{
        Map<String,String> map = new HashMap<>();
        map.put("userId",String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("main/getRecommendListByUserId",map);
        return new BaseJson(result);
    }
}

package com.quickcanteen.quickcanteen.actions.company.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.company.ICompanyAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11022 on 2017/6/30.
 */
public class CompanyActionImpl extends BaseActionImpl implements ICompanyAction {
    public CompanyActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson getCompanyInfoByCompanyId(int companyId) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("companyID", String.valueOf(companyId));
        String result = httpConnectByPost("company/getCompanyInfoByID", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getTypesAndDishesByCompanyId(int companyId) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("companyID", String.valueOf(companyId));
        String result = httpConnectByPost("company/getTypesAndDishesByCompanyId", map);
        return new BaseJson(result);
    }


}

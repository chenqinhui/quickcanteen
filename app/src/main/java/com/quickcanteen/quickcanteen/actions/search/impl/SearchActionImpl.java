package com.quickcanteen.quickcanteen.actions.search.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.search.ISearchAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11022 on 2017/7/3.
 */
public class SearchActionImpl extends BaseActionImpl implements ISearchAction {
    public SearchActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson search(String searchText) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("searchText", searchText);
        String result = httpConnectByPost("search/searchByText", map);
        return new BaseJson(result);
    }
}

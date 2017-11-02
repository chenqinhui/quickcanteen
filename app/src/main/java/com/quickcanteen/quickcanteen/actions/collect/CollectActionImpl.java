package com.quickcanteen.quickcanteen.actions.collect;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CollectActionImpl extends BaseActionImpl implements ICollectAction{
    public CollectActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson getCollectDishesByUserId(int pageNumber,int pageSize)throws IOException, JSONException{
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(getCurrentUserID()));
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", String.valueOf(pageSize));
        String result = httpConnectByPost("dishes/getCollectDishesListByUserId", map);
        return new BaseJson(result);
    }
}

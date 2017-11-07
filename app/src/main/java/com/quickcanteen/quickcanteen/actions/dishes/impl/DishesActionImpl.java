package com.quickcanteen.quickcanteen.actions.dishes.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.dishes.IDishesAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11022 on 2017/6/30.
 */
public class DishesActionImpl extends BaseActionImpl implements IDishesAction {
    public DishesActionImpl(Activity activity) {
        super(activity);
    }


    @Override
    public BaseJson getCollectStatusByUserIdByDishesId(int dishesId) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("dishesId", String.valueOf(dishesId));
        map.put("userId",String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("dishes/getCollectStatusByUserIdByDishesId", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson changeCollectStatusByDishesByUserId(int dishesId,int isCollect) throws IOException, JSONException {
        Map<String, String> map = new HashMap<>();
        map.put("dishesId", String.valueOf(dishesId));
        map.put("userId",String.valueOf(getCurrentUserID()));
        map.put("isCollect",String.valueOf(isCollect));
        String result = httpConnectByPost("dishes/changeCollectStatusByDishesByUserId", map);
        return new BaseJson(result);
    }
}

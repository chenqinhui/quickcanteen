package com.quickcanteen.quickcanteen.actions.dishes;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by 11022 on 2017/6/30.
 */
public interface IDishesAction extends IBaseAction {
    BaseJson getCollectStatusByUserIdByDishesId(int dishesId)throws IOException, JSONException;

    BaseJson changeCollectStatusByDishesByUserId(int dishesId,int isCollect)throws IOException,JSONException;

}

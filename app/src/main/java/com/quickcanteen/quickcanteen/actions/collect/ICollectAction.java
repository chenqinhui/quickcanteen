package com.quickcanteen.quickcanteen.actions.collect;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface ICollectAction extends IBaseAction{
    BaseJson getCollectDishesByUserId(int pageNumber,int pageSize)throws IOException, JSONException;
}

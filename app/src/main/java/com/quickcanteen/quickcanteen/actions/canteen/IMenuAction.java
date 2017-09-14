package com.quickcanteen.quickcanteen.actions.menu;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by 11022 on 2017/6/30.
 */
public interface IMenuAction extends IBaseAction {
    public BaseJson getTypesAndDishesByCompanyId(int companyId) throws IOException,JSONException;
}

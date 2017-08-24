package com.quickcanteen.quickcanteen.actions.company;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by 11022 on 2017/6/30.
 */
public interface ICompanyAction extends IBaseAction {
    BaseJson getCompanyInfoByCompanyId(int companyId)throws IOException, JSONException;
    BaseJson getTypesAndDishesByCompanyId(int companyId)throws IOException, JSONException;
}

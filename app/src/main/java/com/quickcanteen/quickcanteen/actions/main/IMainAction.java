package com.quickcanteen.quickcanteen.actions.main;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Cynthia on 2017/7/11.
 */
public interface IMainAction extends IBaseAction{
    BaseJson getCompanyInfoByPage(int pageNumber, int pageSize) throws IOException,JSONException;
}

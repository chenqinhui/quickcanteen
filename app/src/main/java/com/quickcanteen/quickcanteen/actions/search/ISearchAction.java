package com.quickcanteen.quickcanteen.actions.search;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by 11022 on 2017/7/3.
 */
public interface ISearchAction extends IBaseAction{
    BaseJson search(String searchText)throws IOException,JSONException;
}

package com.quickcanteen.quickcanteen.actions.location;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface ILocationAction extends IBaseAction{
    BaseJson getCurrentUserLocations() throws IOException,JSONException;
}

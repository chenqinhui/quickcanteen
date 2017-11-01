package com.quickcanteen.quickcanteen.actions.location.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.location.ILocationAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocationActionImpl extends BaseActionImpl implements ILocationAction{
    public LocationActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson getCurrentUserLocations() throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("location/getLocationsByUserID", map);
        return new BaseJson(result);
    }
}

package com.quickcanteen.quickcanteen.actions.timeSlot;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TimeSlotActionImpl extends BaseActionImpl implements ITimeSlotAction {
    public TimeSlotActionImpl(Activity activity) {
        super(activity);
    }


    @Override
    public BaseJson getAllTimeSlotsByCompanyId(int companyId) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("companyId",String.valueOf(companyId));
        String result = httpConnectByPost("timeSlot/getAllTimeSlots",map);
        return new BaseJson(result);
    }
}

package com.quickcanteen.quickcanteen.actions.timeSlot;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface ITimeSlotAction extends IBaseAction{
    BaseJson getAllTimeSlotsByCompanyId(int companyId) throws IOException,JSONException;
}

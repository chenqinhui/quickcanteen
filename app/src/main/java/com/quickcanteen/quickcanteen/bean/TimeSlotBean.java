package com.quickcanteen.quickcanteen.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TimeSlotBean {
    Integer timeSlotId;
    Long startTime;
    Long endTime;

    public TimeSlotBean() {
        this.timeSlotId = 0;
        this.startTime = null;
        this.endTime = null;
    }

    public TimeSlotBean(JSONObject jsonObject) throws JSONException {
        this.timeSlotId = jsonObject.getInt("timeSlotId");
        this.startTime = jsonObject.getLong("startTime");
        this.endTime = jsonObject.getLong("endTime");
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}

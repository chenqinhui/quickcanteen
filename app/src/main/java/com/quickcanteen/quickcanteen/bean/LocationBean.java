package com.quickcanteen.quickcanteen.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LocationBean implements Serializable{
    private Integer locationId;

    private Integer userId;

    private Double longitude;

    private Double latitude;

    private String address;

    public LocationBean() {
        locationId = 0;
        userId = 0;
        latitude = 0.0;
        longitude = 0.0;
        address = "";
    }

    public LocationBean(JSONObject jsonObject) throws JSONException {
        locationId = jsonObject.getInt("locationId");
        userId = jsonObject.getInt("userId");
        latitude = jsonObject.getDouble("latitude");
        longitude = jsonObject.getDouble("longitude");
        address = jsonObject.getString("address");
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
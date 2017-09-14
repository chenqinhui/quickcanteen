package com.quickcanteen.quickcanteen.bean;

import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * CompanyInfo entity. @author MyEclipse Persistence Tools
 */

public class CompanyInfoBean implements java.io.Serializable, JsonBean<CompanyInfoBean> {

    // Fields
    private Integer companyId;
    private String accountNumber;
    private String companyName;
    private Double rating;
    private Integer busyDegree;
    private Long startTime;
    private Long endTime;


    // Constructors

    /**
     * default constructor
     */
    public CompanyInfoBean() {
    }


    public CompanyInfoBean(JSONObject jsonObject) throws JSONException {
        this.companyId = jsonObject.getInt("companyId");
        this.companyName = jsonObject.getString("companyName");
        this.accountNumber = jsonObject.getString("accountNumber");
        this.startTime = jsonObject.getLong("startTime");
        this.endTime = jsonObject.getLong("endTime");
        this.busyDegree=jsonObject.getInt("busyDegree");
        this.rating=jsonObject.getDouble("rating");
    }

    @Override
    public CompanyInfoBean newInstance(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public CompanyInfoBean newInstance(BaseJson baseJson) throws JSONException {
        return null;
    }

    // Property accessors


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getBusyDegree() {
        return busyDegree;
    }

    public void setBusyDegree(Integer busyDegree) {
        this.busyDegree = busyDegree;
    }

}
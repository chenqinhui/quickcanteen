package com.quickcanteen.quickcanteen.bean;

import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * CompanyInfo entity. @author MyEclipse Persistence Tools
 */

public class CompanyInfoBean implements java.io.Serializable, JsonBean<CompanyInfoBean> {

    // Fields

    private Integer companyId;
    private String companyName;
    private String accountNumber;
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
}
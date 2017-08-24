package com.quickcanteen.quickcanteen.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 11022 on 2017/7/3.
 */
public class SearchTypeBean {
    private CompanyInfoBean companyInfoBean;
    private Integer typeId;
    private String typeName;

    public SearchTypeBean(JSONObject jsonObject) throws JSONException{
        this.companyInfoBean=new CompanyInfoBean(jsonObject.getJSONObject("companyInfoBean"));
        this.typeId=jsonObject.getInt("typeId");
        this.typeName=jsonObject.getString("typeName");
    }

    public CompanyInfoBean getCompanyInfoBean() {
        return companyInfoBean;
    }

    public void setCompanyInfoBean(CompanyInfoBean companyInfoBean) {
        this.companyInfoBean = companyInfoBean;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}

package com.quickcanteen.quickcanteen.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by 11022 on 2017/7/3.
 */
public class SearchBean {
    private CompanyInfoBean companyInfoBean;
    private Boolean hasType;
    private Boolean hasDishes;
    private ArrayList<SearchTypeBean> searchTypeBeans;
    private ArrayList<DishesBean> dishesBeans;

    public SearchBean(JSONObject jsonObject) throws JSONException {
        this.companyInfoBean=new CompanyInfoBean(jsonObject.getJSONObject("companyInfoBean"));
        this.hasType = jsonObject.getBoolean("hasType");
        this.hasDishes = jsonObject.getBoolean("hasDishes");
        if (hasType) {
            this.searchTypeBeans = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("searchTypeBeans");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                searchTypeBeans.add(new SearchTypeBean(tempJsonObject));
            }
        }
        if (hasDishes) {
            this.dishesBeans = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("dishesBeans");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tempJsonObject = jsonArray.getJSONObject(i);
                dishesBeans.add(new DishesBean(tempJsonObject));
            }
        }
    }

    public CompanyInfoBean getCompanyInfoBean() {
        return companyInfoBean;
    }

    public void setCompanyInfoBean(CompanyInfoBean companyInfoBean) {
        this.companyInfoBean = companyInfoBean;
    }

    public Boolean getHasType() {
        return hasType;
    }

    public void setHasType(Boolean hasType) {
        this.hasType = hasType;
    }

    public Boolean getHasDishes() {
        return hasDishes;
    }

    public void setHasDishes(Boolean hasDishes) {
        this.hasDishes = hasDishes;
    }

    public ArrayList<SearchTypeBean> getSearchTypeBeans() {
        return searchTypeBeans;
    }

    public void setSearchTypeBeans(ArrayList<SearchTypeBean> searchTypeBeans) {
        this.searchTypeBeans = searchTypeBeans;
    }

    public ArrayList<DishesBean> getDishesBeans() {
        return dishesBeans;
    }

    public void setDishesBeans(ArrayList<DishesBean> dishesBeans) {
        this.dishesBeans = dishesBeans;
    }
}

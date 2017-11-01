package com.quickcanteen.quickcanteen.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 11022 on 2017/7/1.
 */
public class OrderBean implements Serializable {
    private Integer orderId;
    private Integer userId;
    private String userRealName;
    private String userTelephone;
    private Integer companyId;
    private String companyName;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private Long publishTime;
    private Long completeTime;
    private ArrayList<DishesBean> dishesBeanList;
    private String timeSlot;
    private Integer deliverManId;
    private Double deliverPrice;
    private LocationBean locationBean;

    public OrderBean(JSONObject jsonObject) throws JSONException {
        this.orderId = jsonObject.getInt("orderId");
        this.userId = jsonObject.getInt("userId");
        this.userRealName = jsonObject.getString("userRealName");
        this.userTelephone = jsonObject.getString("userTelephone");
        this.companyId = jsonObject.getInt("companyId");
        this.companyName = jsonObject.getString("companyName");
        this.totalPrice = jsonObject.getDouble("totalPrice");
        this.orderStatus = OrderStatus.valueOf(jsonObject.getInt("orderStatus"));
        this.publishTime = jsonObject.getLong("publishTime");
        this.completeTime = jsonObject.getLong("completeTime");
        this.dishesBeanList = new ArrayList<>();
        this.timeSlot = jsonObject.getString("timeslotId");
        this.deliverManId = jsonObject.getInt("deliverManId");
        this.deliverPrice = jsonObject.getDouble("deliverPrice");
        JSONArray jsonArray = jsonObject.getJSONArray("dishesBeanList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
            dishesBeanList.add(new DishesBean(tempJsonObject));
        }
        this.locationBean = new LocationBean(jsonObject.getJSONObject("locationBean"));
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<DishesBean> getDishesBeanList() {
        return dishesBeanList;
    }

    public void setDishesBeanList(ArrayList<DishesBean> dishesBeanList) {
        this.dishesBeanList = dishesBeanList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getDeliverManId() {
        return deliverManId;
    }

    public void setDeliverManId(Integer deliverManId) {
        this.deliverManId = deliverManId;
    }

    public Double getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(Double deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }
}

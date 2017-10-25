package com.quickcanteen.quickcanteen.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 11022 on 2017/6/30.
 */
public class DishesBean implements Serializable {
    private Integer dishesId;
    private Integer companyId;
    private Double price;
    private String diagrammaticSketchAddress;
    private String dishesName;
    private String dishesIntroduce;
    private Integer praiseNum;
    private Integer commentNum;
    private Integer collectNum;
    private Double rating;
    private Integer count;
    private Integer available;

    public DishesBean(JSONObject jsonObject) throws JSONException {
        this.dishesId = jsonObject.getInt("dishesId");
        this.companyId = jsonObject.getInt("companyId");
        this.price = jsonObject.getDouble("price");
        this.diagrammaticSketchAddress = jsonObject.getString("diagrammaticSketchAddress");
        this.dishesName = jsonObject.getString("dishesName");
        this.dishesIntroduce = jsonObject.getString("dishesIntroduce");
        this.praiseNum = jsonObject.getInt("praiseNum");
        this.commentNum = jsonObject.getInt("commentNum");
        this.collectNum = jsonObject.getInt("collectNum");
        this.rating = jsonObject.getDouble("rating");
        this.count = jsonObject.getInt("count");
        this.available = jsonObject.getInt("available");
    }

    public Integer getDishesId() {
        return dishesId;
    }

    public void setDishesId(Integer dishesId) {
        this.dishesId = dishesId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDiagrammaticSketchAddress() {
        return diagrammaticSketchAddress;
    }

    public void setDiagrammaticSketchAddress(String diagrammaticSketchAddress) {
        this.diagrammaticSketchAddress = diagrammaticSketchAddress;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getDishesIntroduce() {
        return dishesIntroduce;
    }

    public void setDishesIntroduce(String dishesIntroduce) {
        this.dishesIntroduce = dishesIntroduce;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}

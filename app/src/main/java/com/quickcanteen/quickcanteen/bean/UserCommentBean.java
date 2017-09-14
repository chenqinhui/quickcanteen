package com.quickcanteen.quickcanteen.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Cynthia on 2017/7/14.
 */
public class UserCommentBean implements Serializable {
    // Fields
    private Integer commentId;
    private Integer userId;
    private String commentContent;
    private Integer praiseNum;
    private Double rating;

    public UserCommentBean(JSONObject jsonObject) throws JSONException{
        this.commentId = jsonObject.getInt("commentId");
        this.userId = jsonObject.getInt("userId");
        this.commentContent = jsonObject.getString("commentContent");
        this.praiseNum = jsonObject.getInt("praiseNum");
        this.rating = jsonObject.getDouble("rating");
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

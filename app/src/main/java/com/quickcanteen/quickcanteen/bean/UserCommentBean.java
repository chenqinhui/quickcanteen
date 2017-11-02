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

    private Double rating;

    private String commentContent;

    private String commenterName;

    private String commentTimeStr;

    public UserCommentBean(JSONObject jsonObject) throws JSONException{
        this.commentId = jsonObject.getInt("commentId");
        this.commentContent = jsonObject.getString("commentContent");
        this.rating = jsonObject.getDouble("rating");
        this.commenterName = jsonObject.getString("commenterName");
        this.commentTimeStr = jsonObject.getString("commentTimeStr");
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentTimeStr() {
        return commentTimeStr;
    }

    public void setCommentTimeStr(String commentTimeStr) {
        this.commentTimeStr = commentTimeStr;
    }
}

package com.quickcanteen.quickcanteen.bean;

import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
public class UserInfoBean implements java.io.Serializable, JsonBean<UserInfoBean> {

    // Fields

    private Integer userId;
    private String accountNumber;
    private String headPortrait;
    private String nickName;
    private String realName;
    private String telephone;
    private Long entranceYear;
    private String collegeName;
    private String universityName;
    private Integer isAdmin;
    private Integer points;
    private Boolean deliver;

    @Override
    public UserInfoBean newInstance(JSONObject jsonObject) throws JSONException {
        return new UserInfoBean(jsonObject);
    }

    @Override
    public UserInfoBean newInstance(BaseJson baseJson) throws JSONException {
        return newInstance(baseJson.getJSONObject());
    }

    // Constructors

    /**
     * default constructor
     *
     * @param currentUserInfo
     */
    public UserInfoBean(BaseJson currentUserInfo) {
    }


    /**
     * full constructor
     */

    public UserInfoBean(JSONObject jsonObject) throws JSONException {
        this.userId = jsonObject.getInt("userId");
        this.accountNumber = jsonObject.getString("accountNumber");
        this.headPortrait = jsonObject.getString("headPortrait");
        this.nickName = jsonObject.getString("nickName");
        this.realName = jsonObject.getString("realName");
        this.telephone = jsonObject.getString("telephone");
        this.entranceYear = jsonObject.getLong("entranceYear");
        this.collegeName = jsonObject.getString("collegeName");
        this.universityName = jsonObject.getString("universityName");
        this.isAdmin = jsonObject.getInt("isAdmin");
        this.points = jsonObject.getInt("points");
        this.deliver = jsonObject.getBoolean("deliver");
    }

    // Property accessors

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getEntranceYear() {
        return entranceYear;
    }

    public void setEntranceYear(Long entranceYear) {
        this.entranceYear = entranceYear;
    }

    public String getCollegeName() {
        return this.collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getUniversityName() {
        return this.universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Integer getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getDeliver() {
        return deliver;
    }

    public void setDeliver(Boolean deliver) {
        this.deliver = deliver;
    }
}
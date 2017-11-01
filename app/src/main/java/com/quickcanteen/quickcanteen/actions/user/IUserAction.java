package com.quickcanteen.quickcanteen.actions.user;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by 11022 on 2017/6/29.
 */
public interface IUserAction extends IBaseAction {
    BaseJson login(String accountNumber, String userPassword) throws IOException, JSONException;

    BaseJson register(String accountNumber, String userPassword, String realName, String teleNum) throws IOException, JSONException;

    BaseJson editPassword(int userID, String oldPassword, String newPassword) throws IOException, JSONException;

    BaseJson editUserInfo(String password, int userID, String infoType, String correctInfo) throws IOException, JSONException;

    BaseJson getCurrentUserInfo() throws IOException, JSONException;

    /**
     * 验证用户信息
     *
     * @param accountNumber 学号（账号）
     * @param realName      姓名
     * @return 0表示信息正确，1表示错误
     */
    int validateRealName(String accountNumber, String realName) throws IOException;

    /**
     * 验证手机号
     *
     * @param telephone      手机号
     * @param validateNumber 验证码
     * @return 0表示信息正确，1表示错误
     */
    int validateTeleNum(String telephone, String validateNumber) throws IOException;

    BaseJson signUpForDeliver() throws IOException, JSONException;
}

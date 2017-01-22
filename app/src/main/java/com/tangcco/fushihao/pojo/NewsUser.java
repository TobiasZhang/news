package com.tangcco.fushihao.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsUser implements Serializable {

    public NewsUser(int userId, String userRealName, String userNickName, String userName, String passWrod, String userAddress, int userState) {
        this.userId = userId;
        this.userRealName = userRealName;
        this.userNickName = userNickName;
        this.userName = userName;
        this.passWrod = passWrod;
        this.userAddress = userAddress;
        this.userState = userState;
    }

    private int userId;
    private String userRealName;
    private String userNickName;
    private String userName;
    private String passWrod;
    private String userAddress;
    private int userState;

    public NewsUser(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWrod() {
        return passWrod;
    }

    public void setPassWrod(String passWrod) {
        this.passWrod = passWrod;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }
}

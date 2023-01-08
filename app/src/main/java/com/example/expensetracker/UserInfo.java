package com.example.expensetracker;

import java.util.Map;

public class UserInfo {
    private String userName;
    private String userEmail;
    private String userPassword;
    private Map<String, Object>  groupList;
    private String noOfGroupAdded;


    public Map<String, Object>   getGroupList() {
        return groupList;
    }

    public void setGroupList(Map<String, Object>   groupList) {
        this.groupList = groupList;
    }

    public UserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNoOfGroupAdded() {
        return noOfGroupAdded;
    }

    public void setNoOfGroupAdded(String noOfGroupAdded) {
        this.noOfGroupAdded = noOfGroupAdded;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", groupList=" + groupList +
                ", noOfGroupAdded='" + noOfGroupAdded + '\'' +
                '}';
    }
}

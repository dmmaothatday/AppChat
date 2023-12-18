package com.example.projectappchat.dto;

import java.util.Arrays;

public class UserDTO {
    public Long userId;
    public String userName;
    public byte[] userLogo;
    public String userEmail;
    public String userContactNumber;
    public String facebook;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(byte[] userLogo) {
        this.userLogo = userLogo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userLogo=" + Arrays.toString(userLogo) +
                ", userEmail='" + userEmail + '\'' +
                ", userContactNumber='" + userContactNumber + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}

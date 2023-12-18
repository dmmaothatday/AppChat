package com.example.projectappchat.dto;

public class SearchFriendDTO {
    public Long userId;
    public String userName;
    public byte[] userLogo;
    public byte friendStatus;


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

    public byte getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(byte friendStatus) {
        this.friendStatus = friendStatus;
    }

    @Override
    public String toString() {
        return "SearchFriendDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", friendStatus=" + friendStatus +
                '}';
    }
}


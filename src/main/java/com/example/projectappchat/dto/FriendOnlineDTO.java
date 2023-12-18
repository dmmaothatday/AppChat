package com.example.projectappchat.dto;

import com.example.projectappchat.entity.User;

import java.sql.Date;

public class FriendOnlineDTO {
    Long friendId;
    User friendSendId;
    User friendReceiverId;
    byte friendStatus;
    Date friendDateCreation;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public User getFriendSendId() {
        return friendSendId;
    }

    public void setFriendSendId(User friendSendId) {
        this.friendSendId = friendSendId;
    }

    public User getFriendReceiverId() {
        return friendReceiverId;
    }

    public void setFriendReceiverId(User friendReceiverId) {
        this.friendReceiverId = friendReceiverId;
    }

    public byte getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(byte friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Date getFriendDateCreation() {
        return friendDateCreation;
    }

    public void setFriendDateCreation(Date friendDateCreation) {
        this.friendDateCreation = friendDateCreation;
    }

    @Override
    public String toString() {
        return "FriendOnlineDTO{" +
                "friendId=" + friendId +
                ", friendSendId=" + friendSendId.getUserAccount() +
                ", friendReceiverId=" + friendReceiverId.getUserAccount() +
                ", friendOnline=" + friendReceiverId.getOnline() +
                ", friendStatus=" + friendStatus +
                ", friendDateCreation=" + friendDateCreation +
                '}';
    }
}

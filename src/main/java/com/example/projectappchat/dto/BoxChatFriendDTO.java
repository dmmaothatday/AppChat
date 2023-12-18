package com.example.projectappchat.dto;

import com.example.projectappchat.entity.User;

import java.sql.Date;

public class BoxChatFriendDTO {
    Long friendId;
    User friendSendId;
    User friendReceiverId;
    byte friendStatus;
    Date friendDateCreation;
    String lastMessage;

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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "BoxChatFriendDTO{" +
                "friendId=" + friendId +
                ", friendSendId=" + friendSendId +
                ", friendReceiverId=" + friendReceiverId +
                ", friendStatus=" + friendStatus +
                ", friendDateCreation=" + friendDateCreation +
                ", lastMessage='" + lastMessage + '\'' +
                '}';
    }
}

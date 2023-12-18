package com.example.projectappchat.entity;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_Id")
    private Long userId;
    @Column(name = "User_Name")
    private String userName;
    @Column(name = "User_Account")
    private String userAccount;
    private String userPassword;
    @Lob
    @Column(name = "User_Logo", length = Integer.MAX_VALUE)
    private byte[] userLogo;
    private String userEmail;
    private String userContactNumber;
    private Date userDateCreation;

    @Column(name = "Online")
    private Byte online;

    @Column(name = "Facebook")
    private String facebook;

    @Column(name = "Enabled", length = 1)
    private boolean enabled;

    public User() {
    }

    public User(Long userId, String userName, String userAccount, String userPassword, byte[] userLogo,
                String userEmail, String userContactNumber,
                Date userDateCreation, Byte online, String facebook, boolean enabled) {
        this.userId = userId;
        this.userName = userName;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userLogo = userLogo;
        this.userEmail = userEmail;
        this.userContactNumber = userContactNumber;
        this.userDateCreation = userDateCreation;
        this.online = online;
        this.facebook = facebook;
        this.enabled = enabled;
    }

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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public Date getUserDateCreation() {
        return userDateCreation;
    }

    public void setUserDateCreation(Date userDateCreation) {
        this.userDateCreation = userDateCreation;
    }

    public Byte getOnline() {
        return online;
    }

    public void setOnline(Byte online) {
        this.online = online;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userContactNumber='" + userContactNumber + '\'' +
                ", userDateCreation=" + userDateCreation +
                ", online=" + online +
                ", enabled=" + enabled +
                '}';
    }
}

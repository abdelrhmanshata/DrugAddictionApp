package com.example.drugaddictionapp.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String uID, UserName, uEmail, uPassword, uPhone, uAge, uLocation;

    public User() {
    }

    public User(String uID, String userName, String uEmail, String uPassword, String uPhone, String uAge, String uLocation) {
        this.uID = uID;
        UserName = userName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uPhone = uPhone;
        this.uAge = uAge;
        this.uLocation = uLocation;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuAge() {
        return uAge;
    }

    public void setuAge(String uAge) {
        this.uAge = uAge;
    }

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }
}

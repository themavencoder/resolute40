package com.aloine.resolute40.auth.quickLogin.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    private String pinOne;
    private String pinTwo;
    private String pinThree;
    private String pinFour;

    @SerializedName("pin")
    private String concatPin;

    @SerializedName("phone")
    private String phone;


    public LoginModel() {

    }
    public LoginModel(String pinOne, String pinTwo, String pinThree, String pinFour) {
        this.pinOne = pinOne;
        this.pinTwo = pinTwo;
        this.pinThree = pinThree;
        this.pinFour = pinFour;


    }

    public LoginModel(String phone, String concatPin) {
        this.concatPin = concatPin;
        this.phone = phone;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConcatPin() {
        return concatPin;
    }

    public String getPinFour() {
        return pinFour;
    }

    public String getPinOne() {
        return pinOne;
    }

    public String getPinThree() {
        return pinThree;
    }

    public void setConcatPin(String concatPin) {
        this.concatPin = concatPin;
    }

    public void setPinFour(String pinFour) {
        this.pinFour = pinFour;
    }

    public void setPinOne(String pinOne) {
        this.pinOne = pinOne;
    }

    public void setPinThree(String pinThree) {
        this.pinThree = pinThree;
    }

    public String getPinTwo() {
        return pinTwo;
    }

    public void setPinTwo(String pinTwo) {
        this.pinTwo = pinTwo;
    }
}

package com.aloine.resolute40.auth.login.model;

public class LoginModel {

    private String pinOne;
    private String pinTwo;
    private String pinThree;
    private String pinFour;


    private String concatPin;


    public LoginModel() {

    }
    public LoginModel(String pinOne, String pinTwo, String pinThree, String pinFour) {
        this.pinOne = pinOne;
        this.pinTwo = pinTwo;
        this.pinThree = pinThree;
        this.pinFour = pinFour;


    }

    public LoginModel(String concatPin) {
        this.concatPin = concatPin;

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

package com.aloine.resolute40.auth.formalLogin.model;

public class FormalModel {

  private String phoneNumber;

  private String pin;

  public FormalModel() {

  }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

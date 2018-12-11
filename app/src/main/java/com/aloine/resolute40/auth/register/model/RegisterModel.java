package com.aloine.resolute40.auth.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("community")
    private String community;


    @SerializedName("pin")
    private String pin;

    public RegisterModel() {


    }

    public RegisterModel(String full_name, String phone_number, String community, String pin) {

        this.full_name = full_name;
        this.phone_number = phone_number;
        this.community = community;
        this.pin = pin;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

package com.aloine.resolute40.auth.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("community")
    private String community;


    @SerializedName("pin")
    private String pin;

    @SerializedName("user_type")
    private String user_type;

    public RegisterModel() {


    }

    public RegisterModel(String full_name, String phone_number, String community, String pin, String user_type) {

        this.full_name = full_name;
        this.phone = phone_number;
        this.community = community;
        this.pin = pin;
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

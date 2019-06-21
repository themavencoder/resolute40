package com.aloine.resolute40.panicalert.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PanicDetails implements Parcelable {

    @SerializedName("username")
    private String username;

    @SerializedName("details")
    private String details;

    @SerializedName("panic_status")
    private String panic_status;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;


    public PanicDetails() {

    }

    public PanicDetails(String username, String details, String panic_status, double lat, double lng) {
        this.username = username;
        this.details =details;
        this.panic_status = panic_status;
        this.lat = lat;
        this.lng = lng;
    }

    protected PanicDetails(Parcel in) {
        username = in.readString();
        details = in.readString();
        panic_status = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<PanicDetails> CREATOR = new Creator<PanicDetails>() {
        @Override
        public PanicDetails createFromParcel(Parcel in) {
            return new PanicDetails(in);
        }

        @Override
        public PanicDetails[] newArray(int size) {
            return new PanicDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(details);
        dest.writeString(panic_status);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    public String getUsername() {
        return username;
    }

    public String getPanic_status() {
        return panic_status;
    }

    public String getDetails() {
        return details;
    }

}

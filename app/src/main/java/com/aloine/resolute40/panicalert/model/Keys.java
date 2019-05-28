package com.aloine.resolute40.panicalert.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Keys implements Parcelable {

    @SerializedName("client_token")
    private String client_token;

    @SerializedName("session_token")
    private String session_token;

    public Keys(String client_token, String session_token) {
        this.client_token =client_token;
        this.session_token = session_token;

    }

    protected Keys(Parcel in) {
        client_token = in.readString();
        session_token = in.readString();
    }

    public static final Creator<Keys> CREATOR = new Creator<Keys>() {
        @Override
        public Keys createFromParcel(Parcel in) {
            return new Keys(in);
        }

        @Override
        public Keys[] newArray(int size) {
            return new Keys[size];
        }
    };

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(client_token);
        dest.writeString(session_token);
    }
}

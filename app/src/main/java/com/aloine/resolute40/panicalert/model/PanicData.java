package com.aloine.resolute40.panicalert.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PanicData implements Parcelable {

    @SerializedName("auth")
    private Keys auth;

    @SerializedName("data")
    private PanicDetails data;

    @SerializedName("user_type")
    private String user_type;

    public PanicData() {


    }
    public PanicData(Keys auth, PanicDetails data, String user_type) {
        this.auth = auth;
        this.data = data;
        this.user_type = user_type;
    }

    protected PanicData(Parcel in) {
        user_type = in.readString();
    }

    public static final Creator<PanicData> CREATOR = new Creator<PanicData>() {
        @Override
        public PanicData createFromParcel(Parcel in) {
            return new PanicData(in);
        }

        @Override
        public PanicData[] newArray(int size) {
            return new PanicData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_type);
    }
}

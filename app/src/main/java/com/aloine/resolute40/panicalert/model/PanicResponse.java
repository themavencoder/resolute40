package com.aloine.resolute40.panicalert.model;

import com.google.gson.annotations.SerializedName;

public class PanicResponse {


    @SerializedName("terminate_panic")
    private Boolean terminate_panic;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getTerminate_panic() {
        return terminate_panic;
    }

    public void setTerminate_panic(Boolean terminate_panic) {
        this.terminate_panic = terminate_panic;
    }
}

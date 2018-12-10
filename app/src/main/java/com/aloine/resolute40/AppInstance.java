package com.aloine.resolute40;

import java.util.ArrayList;
import java.util.List;

public class AppInstance {

    private static AppInstance sInstance = null;

    private List<ArrayList<Double>> pointsList = new ArrayList<>();

    public static AppInstance getInstance() {
        if (sInstance == null) {
            sInstance = new AppInstance();
        }
return sInstance;

    }

    public static AppInstance getsInstance() {
        return sInstance;
    }

    public void setPointsList(List<ArrayList<Double>> pointsList) {
        this.pointsList = pointsList;
    }
}

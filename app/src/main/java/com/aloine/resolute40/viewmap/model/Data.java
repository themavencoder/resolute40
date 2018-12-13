package com.aloine.resolute40.viewmap.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public  class Data {

    private static ArrayList<LatLng> serverPointsInLatLng;

    public static ArrayList<LatLng> getServerPointsInLatLng() {
        return serverPointsInLatLng;
    }

    public static   void setServerPointsInLatLng(ArrayList<LatLng> serverPointsInLatLngg) {
        serverPointsInLatLng = serverPointsInLatLngg;
    }
}

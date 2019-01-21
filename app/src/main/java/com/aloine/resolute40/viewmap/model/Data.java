package com.aloine.resolute40.viewmap.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public  class Data {

    private static ArrayList<LatLng> serverPointsInLatLng;

    public static ArrayList<LatLng> getServerPointsInLatLng() {
        return serverPointsInLatLng;
    }

    private static  ArrayList<Float> startLatLngInFloat = new ArrayList<>();
    private static ArrayList<Float> endLatLngInFloat = new ArrayList<>();

    public static void setEndLatLngInFloat(ArrayList<Float> endLatLngInFloat) {
        Data.endLatLngInFloat = endLatLngInFloat;
    }

    public static void setStartLatLngInFloat(ArrayList<Float> startLatLngInFloat) {
        Data.startLatLngInFloat = startLatLngInFloat;
    }

    public static ArrayList<Float> getEndLatLngInFloat() {
        return endLatLngInFloat;
    }

    public static ArrayList<Float> getStartLatLngInFloat() {
        return startLatLngInFloat;
    }

    public static   void setServerPointsInLatLng(ArrayList<LatLng> serverPointsInLatLngg) {
        serverPointsInLatLng = serverPointsInLatLngg;
    }
}

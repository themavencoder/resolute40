package com.aloine.resolute40;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AppInstance {

    private static AppInstance sInstance = null;

    private ArrayList<ArrayList<Double>> pointsList = new ArrayList<>();
    private ArrayList<ArrayList<LatLng>> pointsListLatlng = new ArrayList<>();
    private List<LatLng> pointsLatlngAsList = new ArrayList<>();
    private ArrayList<LatLng> realList = new ArrayList<>();
    private ArrayList<Double> arrLatitude = new ArrayList<>();
    private ArrayList<Double> arrLongitude = new ArrayList<>();

    public static AppInstance getInstance() {
        if (sInstance == null) {
            sInstance = new AppInstance();
        }
return sInstance;

    }

    public ArrayList<LatLng> getRealList() {
        return realList;
    }

    public void setRealList(ArrayList<LatLng> realList) {
        this.realList = realList;
    }

    public List<LatLng> getPointsLatlngAsList() {
        return pointsLatlngAsList;
    }

    public void setPointsLatlngAsList(List<LatLng> pointsLatlngAsList) {
        this.pointsLatlngAsList = pointsLatlngAsList;
    }

    public ArrayList<ArrayList<LatLng>> getPointsListLatlng() {
        return pointsListLatlng;
    }

    public void setPointsListLatlng(ArrayList<ArrayList<LatLng>> pointsListLatlng) {
        this.pointsListLatlng = pointsListLatlng;
    }

    public static AppInstance getsInstance() {
        return sInstance;
    }

    public void setPointsList(ArrayList<ArrayList<Double>> pointsList) {
        this.pointsList = pointsList;
    }

    public ArrayList<ArrayList<Double>> getPointsList() {
        return pointsList;
    }

    public ArrayList<Double> getArrLatitude() {
        return arrLatitude;
    }

    public ArrayList<Double> getArrLongitude() {
        return arrLongitude;
    }

    public void setArrLatitude(ArrayList<Double> arrLatitude) {
        this.arrLatitude = arrLatitude;
    }

    public void setArrLongitude(ArrayList<Double> arrLongitude) {
        this.arrLongitude = arrLongitude;
    }
}

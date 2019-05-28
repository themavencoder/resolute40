package com.aloine.resolute40;

import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.database.table.Farmer_Table;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
    private String client_token;
    private String session_token;
    private String username;
    private String usertype;
    private Farmer farmer;


    private ArrayList<ArrayList<Float>> pointsToServer = new ArrayList<>();

    public static AppInstance getInstance() {
        if (sInstance == null) {
            sInstance = new AppInstance();
        }
return sInstance;

    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        farmer = SQLite.select().from(Farmer.class).where(Farmer_Table.id.eq(1)).querySingle();
        if (farmer.getPhone_number() != null) {
            this.username = farmer.getPhone_number();
        } else {
            this.username = username;
        }

    }

    public void setPointsToServer(ArrayList<ArrayList<Float>> pointsToServer) {
        this.pointsToServer = pointsToServer;
    }

    public ArrayList<ArrayList<Float>> getPointsToServer() {
        return pointsToServer;
    }

    public String getClient_token() {
        return client_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
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

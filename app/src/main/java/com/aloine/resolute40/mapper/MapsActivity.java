package com.aloine.resolute40.mapper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.R;

public class MapsActivity extends AppCompatActivity implements LocationListener {
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private TextView latitude, longitude;
    private double  lat_d, long_d;
    private static int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocationLatLong();
    }

    private void getLocationLatLong() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        }
        else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            String format = String.format("The latitude is %.2f and the longitdue is %.2f",lat_d,long_d);
            Toast.makeText(this, format, Toast.LENGTH_SHORT).show();

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,2000,0,this);
            if (location != null) {
                lat_d = location.getLatitude();
                long_d = location.getLongitude();
                String formatt = String.format("The latitude is %.2f and the longitdue is %.2f",lat_d,long_d);
                Toast.makeText(this, formatt, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*private void init() {
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
    }
*/

    public void getLocation(View view) {
        getLocationLatLong();
        latitude.setText(String.valueOf(lat_d));
        longitude.setText(String.valueOf(long_d));


    }

    @Override
    public void onLocationChanged(Location location) {

            lat_d = location.getLatitude();
            long_d = location.getLongitude();


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

package com.aloine.resolute40.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.aloine.resolute40.R;
import com.aloine.resolute40.smartLocation.dialog.MyDialog;

import im.delight.android.location.SimpleLocation;

public class MapsActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private SimpleLocation location;
    SimpleLocation.Point position;
    Context context = this;
    boolean requireFineGranularity = true;
    boolean passiveMode = true;
    long updateIntervalInMilliseconds = 2000;
    boolean requireNewLocation = true;

    private TextView latitude, longitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

        location = new SimpleLocation(context, requireFineGranularity, passiveMode, updateIntervalInMilliseconds, requireNewLocation);
    // position =  location.getPosition();



        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }


    }

    /*private void init() {
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
    }*/


    public void getLocation(View view) {

        final double lat = location.getLatitude();
       final double longg = location.getLongitude();
        latitude.setText(String.valueOf(lat));
        longitude.setText(String.valueOf(longg));
    }

    @Override
    protected void onResume() {
        super.onResume();

        location.beginUpdates();

    }

    @Override
    protected void onPause() {
        location.endUpdates();
        super.onPause();
    }

    public void pauseMap(View view) {
        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(), "my_dialog");
    }
}

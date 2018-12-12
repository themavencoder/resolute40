package com.aloine.resolute40.viewmap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    private GoogleMap mMap;
    private ArrayList<LatLng> pointsList = new ArrayList<>();
    private ArrayList<Double> arrLatitude = new ArrayList<>();
    private ArrayList<Double> arrLongitude = new ArrayList<>();
    private ArrayList<LatLng> realList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* AppInstance app =  AppInstance.getInstance();
       pointsList = app.getPointsLatlngAsList();
      // Double[] doubleArray = (Double[]) arrLatitude.toArray();
       double latitude;
       double longitude;
       arrLatitude = app.getArrLatitude();
        Toast.makeText(this, "The latitude size is" + arrLatitude.size(), Toast.LENGTH_SHORT).show();
       arrLongitude = app.getArrLongitude();
        Toast.makeText(this, "The longitude size is" + arrLongitude.size(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < arrLatitude.size(); i++) {
         for (int j = 0; i < arrLongitude.size(); j++) {
             latitude = arrLatitude.get(i);
             longitude = arrLongitude.get(j);
             LatLng myLocation = new LatLng(latitude,longitude);
             if (i == 0 && j == 0) {
                 googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
             }

            googleMap.addMarker(new MarkerOptions().position(myLocation).title("Farm location"));

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14.5f));
            googleMap.setBuildingsEnabled(true);
            googleMap.addPolyline(new PolylineOptions().clickable(true).color(R.color.colorPrimaryDark).add(myLocation));

         }
         googleMap.setOnPolylineClickListener(this);
         googleMap.setOnPolygonClickListener(this);


     }*/

            AppInstance app = AppInstance.getInstance();
            arrLatitude = app.getArrLatitude();
            arrLongitude = app.getArrLongitude();
            realList = app.getRealList();
            Toast.makeText(this, "Your oresent location is" + arrLatitude.get(0) + "and longitude" + arrLongitude.get(0), Toast.LENGTH_SHORT).show();


           PolylineOptions polygonOptions = new PolylineOptions();
           polygonOptions.addAll(realList);
           polygonOptions.width(6);
           polygonOptions.color(R.color.colorPrimaryDark);
           googleMap.addPolyline(polygonOptions);
        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arrLatitude.get(0), arrLongitude.get(0)), 20));
        googleMap.setBuildingsEnabled(true);

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }


    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }


}

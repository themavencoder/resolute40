package com.aloine.resolute40.viewmap.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.database.table.Farmer_Table;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.viewmap.dialog.MyDialog;
import com.aloine.resolute40.viewmap.model.Data;
import com.aloine.resolute40.viewmap.network.ApiService;
import com.aloine.resolute40.viewmap.network.ViewMapResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    private GoogleMap mMap;
    private ArrayList<LatLng> pointsList = new ArrayList<>();
    private ArrayList<Double> arrLatitude = new ArrayList<>();
    private ArrayList<Double> arrLongitude = new ArrayList<>();
    private ArrayList<LatLng> realList = new ArrayList<>();
    private ArrayList<ArrayList<Float>> serverPointsInFloat = new ArrayList<>();
    private ArrayList<LatLng> serverPointsInLatLng = new ArrayList<>();

    private Farmer farmer;
    private ApiService mApiService;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
//        String username = farmer.getPhone_number();
        //Toast.makeText(this, "Is the phone number" +username, Toast.LENGTH_SHORT).show();
AppInstance appInstance = AppInstance.getInstance();
        getMap(appInstance.getUsername());
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
        myDialog = new MyDialog();
        myDialog.setCancelable(false);
        myDialog.show(getSupportFragmentManager(), "my_dialog");
        farmer = SQLite.select().from(Farmer.class).where(Farmer_Table.id.eq(1)).querySingle();

        AppInstance app = AppInstance.getInstance();
        arrLatitude = app.getArrLatitude();
        arrLongitude = app.getArrLongitude();
        realList = app.getRealList();


        PolylineOptions polygonOptions = new PolylineOptions();
        if (Data.getServerPointsInLatLng() != null)  {
            polygonOptions.addAll(Data.getServerPointsInLatLng());
        }
        polygonOptions.width(6);
        polygonOptions.color(R.color.colorPrimaryDark);
        googleMap.addPolyline(polygonOptions);
        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(farmer.getFirst_latitude(), farmer.getFirst_longitude()), 15));
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

    private void getMap(String username) {
        mApiService = Client.getClient().create(ApiService.class);
        Call<ViewMapResponse> call = mApiService.getLocation(username);
        call.enqueue(new Callback<ViewMapResponse>() {
            @Override
            public void onResponse(Call<ViewMapResponse> call, Response<ViewMapResponse> response) {
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().equals("success")) {
                        myDialog.dismiss();
                        Toast.makeText(ViewMapActivity.this, "success in request", Toast.LENGTH_SHORT).show();
                        serverPointsInFloat = response.body().getData();
                        if (serverPointsInFloat != null) {
                            Data.setServerPointsInLatLng(convertToLatLng(serverPointsInFloat));
                        }
                    } else if (response.body().getResponse().equals("failed")) {
                        myDialog.dismiss();
                        Toast.makeText(ViewMapActivity.this, "not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewMapResponse> call, Throwable t) {
                myDialog.dismiss();
                Toast.makeText(ViewMapActivity.this, "no network", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private ArrayList<LatLng> convertToLatLng(ArrayList<ArrayList<Float>> serverPointsInFloat) {
        ArrayList<Float> singleFloat = new ArrayList<>();
        ArrayList<LatLng> latLng = new ArrayList<>();
    for (int i = 0; i < serverPointsInFloat.size(); i++) {
       singleFloat =  serverPointsInFloat.get(i);
       latLng.add(new LatLng(Double.parseDouble(String.valueOf(singleFloat.get(0))),Double.parseDouble(String.valueOf(singleFloat.get(1)))));
    }
    return latLng;

    }


}

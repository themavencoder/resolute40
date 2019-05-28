package com.aloine.resolute40.smartLocation;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.database.table.Farmer_Table;
import com.aloine.resolute40.smartLocation.dialog.MyDialog;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationBasedOnActivityProvider;

public class MapsActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private TextView latitude, longitude;
    private double lat_d, long_d;
    private static int check = 0;
    private DoubleBounce doubleBounce;
    private ProgressBar progressBar;
    private ObjectAnimator progressAnimator;
    private Double lat_temp = 0.00;
    private Double long_temp = 0.00;
    private Button button;
    private ArrayList<ArrayList<Double>> pointsList = new ArrayList<>();
    private ArrayList<LatLng> realList = new ArrayList<>();
    private ArrayList<ArrayList<LatLng>> pointsListLatlng = new ArrayList<>();
    private List<LatLng> pointsLatlngAsList = new ArrayList<>();
    private ArrayList<Double> arrLatitude = new ArrayList<>();
    private ArrayList<Double> arrLongitude = new ArrayList<>();
    private Iterable<LatLng> pointIterable = new ArrayList<>();
    private Double d_lat = 0.00;
    private Double d_long = 0.00;
    private AppInstance appInstance;
    private CoordinatorLayout mCoordinatorLayout;
    public static int points = 0;


    private ArrayList<ArrayList<Float>> pointsToServer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        button = findViewById(R.id.btn_pause);


        init();



       /*progressBar = findViewById(R.id.spin_kit);

       doubleBounce = new DoubleBounce();
       doubleBounce.setScale(0.8f);

        progressBar.setIndeterminateDrawable(doubleBounce);
*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getLocation();


    }

    public void pauseMap(View view) {
        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(), "my_dialog");
    }


    private void init() {
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    }


    public void getLocation() {
        SmartLocation.with(this).location().getLastLocation();
        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {


                 d_lat = location.getLatitude();
                 d_long = location.getLongitude();
                if (d_lat != null && d_long != null) {
                    latitude.setText(String.valueOf(location.getLatitude()));
                    longitude.setText(String.valueOf(location.getLongitude()));

                    if ((lat_temp != d_lat) || (long_temp != d_long)) {
                        if (latitude.getText().toString().equals(String.valueOf(lat_temp)) || longitude.getText().toString().equals(String.valueOf(long_temp)))  {

                        } else {
                            ArrayList<Double> d1 = new ArrayList<>();
                            ArrayList<LatLng> d2 = new ArrayList<>();
                            ArrayList<Float> d1Float = new ArrayList<>();

                            d1.add(d_lat);
                            d1.add(d_long);
                          d1Float.add(Float.valueOf(String.valueOf(d_lat)));
                          d1Float.add(Float.valueOf(String.valueOf(d_long)));
                            d2.add(new LatLng(d_lat,d_long));
                            arrLatitude.add(d_lat);
                            arrLongitude.add(d_long);
                            pointsListLatlng.add(d2);
                            pointsLatlngAsList.add(new LatLng(d_lat,d_long));
                            pointsList.add(d1);
                            pointsToServer.add(d1Float);

                            appInstance = AppInstance.getInstance();
                            appInstance.setPointsList(pointsList);
                            appInstance.setPointsListLatlng(pointsListLatlng);
                            appInstance.setPointsLatlngAsList(pointsLatlngAsList);
                            appInstance.setArrLatitude(arrLatitude);
                            Farmer farmer = new Farmer();
                            farmer.setId(1);
                            farmer.setFirst_latitude(arrLatitude.get(0));
                            farmer.setFirst_longitude(arrLongitude.get(0));
                            farmer.save();
                            appInstance.setArrLongitude(arrLongitude);
                            appInstance.setPointsToServer(pointsToServer);
                            realList.add(new LatLng(d_lat,d_long));
                            appInstance.setRealList(realList);
                            points++;
                            Snackbar snackbar =  showSavedAlert();
                            snackbar.show();


                            //Toast.makeText(MapsActivity.this, "The length of the real list is " + pointsList.size(), Toast.LENGTH_SHORT).show();
                            lat_temp = d_lat;
                            long_temp = d_long;

                        }

                    }
                    getLocation();

                }




              //   Toast.makeText(MapsActivity.this, "The length of the real list is " + pointsList.size(), Toast.LENGTH_SHORT).show();

            }

        });




           /* if ((lat_temp != d_lat) || (long_temp != d_long)) {
                ArrayList<Double> d1 = new ArrayList<>();
                d1.add(d_lat);
                d1.add(d_long);
                pointsList.add(d1);
                Toast.makeText(MapsActivity.this, "The length of the real list is " + pointsList.size(), Toast.LENGTH_SHORT).show();
                lat_temp = d_lat;
                long_temp = d_long;



            }*/
           //  Toast.makeText(MapsActivity.this, "The length of the real list is " + pointsList.size(), Toast.LENGTH_SHORT).show();


        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SmartLocation.with(this).location().stop();
        pointsList.clear();
        points = 0;
    }

    private Snackbar showSavedAlert() {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Point " + points + " collected and saved for mapping", Snackbar.LENGTH_SHORT);

        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }


}




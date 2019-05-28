package com.aloine.resolute40.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.MainActivity;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.database.table.Farmer_Table;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.extras.LocationFinder;
import com.aloine.resolute40.extras.droidbyme.AppConstants;
import com.aloine.resolute40.extras.droidbyme.GpsUtils;
import com.aloine.resolute40.panicalert.dialog.PanicDialog;
import com.aloine.resolute40.panicalert.model.Keys;
import com.aloine.resolute40.panicalert.model.PanicData;
import com.aloine.resolute40.panicalert.model.PanicDetails;
import com.aloine.resolute40.panicalert.model.PanicResponse;
import com.aloine.resolute40.panicalert.network.ApiService;
import com.aloine.resolute40.smartLocation.StartMappingActivity;
import com.aloine.resolute40.viewmap.activity.ViewMapActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {
    public static final String USER_TYPE = "user_type";
    private GridLayout gridLayout;
    private PanicDialog panicDialog;
    private CoordinatorLayout mCoordinatorLayout;
    private Boolean b;
    public double d_lat, d_long;
    private LocationGooglePlayServicesProvider provider;
private LocationManager locationManager;
private FusedLocationProviderClient mFusedLocationClient;
private LocationFinder locationFinder;
private LocationRequest locationRequest;
private LocationCallback locationCallback;
private Farmer farmer;
private Keys keys;
private AppInstance app;
private PanicData panicData;
public static final String PANIC_DATA_KEY = "panic_data_key";
public static final String PANIC_DETAILS_KEY = "panic_details_key";
public static final String PANIC_KEYS = "panic_keys";
private PanicDetails panicDetails;

    private boolean isContinue = false;
    private boolean isGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       app = AppInstance.getInstance();
         keys = new Keys(app.getClient_token(), app.getSession_token());

        farmer = SQLite.select().from(Farmer.class).where(Farmer_Table.id.eq(1)).querySingle();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds
        /*LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String laty = intent.getStringExtra(LocationFinder.EXTRA_LATITUDE);
                String longy = intent.getStringExtra(LocationFinder.EXTRA_LONGITUDE);
                    if (laty != null && longy != null) {
                        Toast.makeText(context, "The latitude is" + laty + "and your longitude is " + longy, Toast.LENGTH_SHORT).show();
                    }
            }
        },new IntentFilter(LocationFinder.ACTION_LOCATION_BROADCAST)
        );
*/
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                isGPS = isGPSEnable;
            }
        });
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        d_lat = location.getLatitude();
                        d_long = location.getLongitude();
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };






        if (app.getUsertype().toLowerCase().equals("herdsman")) {
            setContentView(R.layout.herdsmen_dashboard);
            b = false;
        } else {
            setContentView(R.layout.dashboard);
            b = true;
        }
locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        hideNavigationBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        init();
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }


       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
*/
    //   getLocation();
    // createLocationRequest();

    }
    private void panicClicked() {
        if (!isGPS) {
            Toast.makeText(DashboardActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        isContinue = true;
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(DashboardActivity.this, new OnSuccessListener<Location>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            d_lat = location.getLatitude();
                            d_long = location.getLongitude();

                        } else {
                            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    }
                });
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(DashboardActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    d_lat = location.getLatitude();
                                    d_long = location.getLongitude();
                                } else {
                                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

/*    private void createLocationRequest() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(DashboardActivity.this, "OnLocationResult is null", Toast.LENGTH_SHORT).show();


                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Toast.makeText(DashboardActivity.this, "Location is not null", Toast.LENGTH_SHORT).show();
                        d_lat = location.getLatitude();
                        d_long = location.getLongitude();
                    } else {
                        Toast.makeText(DashboardActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }*/

    private void init() {
        gridLayout = findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);
        panicDialog = new PanicDialog();
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    }


    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            Log.i("Information", "value of " + finalI);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (b) {
                        switch (finalI) {
                            case 0:
                                startActivity(new Intent(DashboardActivity.this, StartMappingActivity.class));
                                break;
                            case 1:

                                //requestLocation(locationFinder);
                                panicClicked();
                                panicDialog.setCancelable(false);
                                panicDialog.show(getSupportFragmentManager(), "my_dialog");
                                AppInstance app = AppInstance.getInstance();
                                 keys = new Keys(app.getClient_token(), app.getSession_token());
                              //  createLocationRequest();
                                if (d_lat == 0.0 && d_long == 0.0) {
                                    Toast.makeText(DashboardActivity.this, "Unable to get location, try again", Toast.LENGTH_SHORT).show();
                                    panicDialog.dismiss();
                                    return;
                                }
                                 panicDetails = new PanicDetails(app.getUsername(), "A panic has been sent", "True", d_lat, d_long);
                                 panicData = new PanicData(keys, panicDetails, app.getUsertype());
                                panicNetworkRequest(panicData);
                                break;
                            case 2:
                                startActivity(new Intent(DashboardActivity.this, ViewMapActivity.class));
                                break;
                            case 3:
                                Toast.makeText(DashboardActivity.this, "Still in production", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }

                    } else {

                        switch (finalI) {
                            case 0:
                                panicDialog.setCancelable(false);
                                panicDialog.show(getSupportFragmentManager(), "my_dialog");
                                AppInstance app = AppInstance.getInstance();

                                PanicDetails panicDetails = new PanicDetails(app.getUsername(), "A panic has been sent", "True", 0.0f, 0.0f);
                                 panicData = new PanicData(keys, panicDetails, app.getUsertype());
                                panicNetworkRequest(panicData);
                                break;
                            case 1:
                                Toast.makeText(DashboardActivity.this, "Still in production", Toast.LENGTH_SHORT).show();
                                break;
                            default:

                        }
                    }


                }
            });
        }
    }
    
  /*  private void getLocation() {
        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);
        SmartLocation.with(this).location(provider).config(LocationParams.BEST_EFFORT).start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                d_lat = location.getLatitude();
                d_long = location.getLongitude();
                Log.d("LOCATIONOFMAP",String.valueOf(d_lat));
                Log.d("LOCATIONOFMAP",String.valueOf(d_long));
            }
        });

        SmartLocation.with(this).location().getLastLocation();
        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                d_lat = location.getLatitude();
                d_long = location.getLongitude();
                Log.d("LOCATIONOFMAP",String.valueOf(d_lat));
                Log.d("LOCATIONOFMAP",String.valueOf(d_long));
              //  Toast.makeText(DashboardActivity.this, "The longitude is " + d_long + "the latitude is " + d_lat , Toast.LENGTH_SHORT).show();

            }
        });
    }
*/
    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }


    private void panicNetworkRequest(PanicData data) {
        locationFinder = new LocationFinder();
        ApiService apiService;
        apiService = Client.getClient().create(ApiService.class);
        Call<PanicResponse> call = apiService.postLocation(data);
        call.enqueue(new Callback<PanicResponse>() {
            @Override
            public void onResponse(Call<PanicResponse> call, Response<PanicResponse> response) {
                if (response.body().getResponse() != null) {
                    switch (response.body().getResponse()) {
                        case "success":

                            panicDialog.dismiss();
                            Snackbar snackbar = success("Your panic was sent to the Resolute 4.0");
                            Intent intent = new Intent(getApplicationContext(),LocationFinder.class);
                            intent.putExtra(PANIC_DATA_KEY,panicData);
                            intent.putExtra(PANIC_DETAILS_KEY,panicDetails);
                            intent.putExtra(PANIC_KEYS,keys);
                            intent.putExtra(USER_TYPE, app.getUsertype());
                            intent.setAction(LocationFinder.ACTION_START_FOREGROUND_SERVICE);
                            startService(intent);

                            snackbar.show();
                            break;
                        case "failed":
                            panicDialog.dismiss();
                            Snackbar snack = error("Your panic was not sent");
                            snack.show();

                            break;
                        default:
                            panicDialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<PanicResponse> call, Throwable t) {
                panicDialog.dismiss();
                Snackbar snackbar = error("Your network connection is a bit slow");
                snackbar.show();

            }
        });


    }

    private Snackbar error(String s) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }

    private Snackbar success(String s) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;

    }
    private void requestLocation(LocationFinder finder) {
 /*       Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider  = locationManager.getBestProvider(criteria,true);*/
 if (finder.canGetLocation()) {
            d_lat = finder.getLatitude();
            d_long = finder.getLongitude();
     Toast.makeText(this, "lat-long" + d_lat + "-" + d_long, Toast.LENGTH_SHORT).show();

 }


    }
    private void requestLocationFused() {

    }

}

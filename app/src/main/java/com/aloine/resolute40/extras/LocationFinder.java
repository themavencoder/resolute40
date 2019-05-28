package com.aloine.resolute40.extras;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.dashboard.DashboardActivity;
import com.aloine.resolute40.panicalert.model.Keys;
import com.aloine.resolute40.panicalert.model.PanicData;
import com.aloine.resolute40.panicalert.model.PanicDetails;
import com.aloine.resolute40.panicalert.model.PanicResponse;
import com.aloine.resolute40.panicalert.network.ApiService;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aloine.resolute40.dashboard.DashboardActivity.PANIC_DATA_KEY;
import static com.aloine.resolute40.dashboard.DashboardActivity.PANIC_DETAILS_KEY;
import static com.aloine.resolute40.dashboard.DashboardActivity.PANIC_KEYS;
import static com.aloine.resolute40.dashboard.DashboardActivity.USER_TYPE;

/**
 * Created by themavencoder on 03,April,2019
 */
public class LocationFinder extends Service implements LocationListener {

    Context context;
    boolean isNetworkEnabled = false;
    boolean isGPSEnabled = false;
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    public static final String EXTRA_LATITUDE = "extra_latitude";
    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String ACTION_LOCATION_BROADCAST = LocationFinder.class.getName() + "LocationBroadcast";
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 500 * 10 * 1; // 5 seconds
    // Declaring a Location Manager
    protected LocationManager locationManager;
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    private AppInstance appInstance = AppInstance.getInstance();
    private PanicData panicData;
    private PanicDetails panicDetails;
    private Keys keys;
    private String userType;
    private final IBinder mBinder = new MyBinder();


    public LocationFinder() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public LocationFinder(Context context) {
        this.context = context;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
        //    panicData = intent.getParcelableExtra(PANIC_DATA_KEY);
            userType = intent.getStringExtra(USER_TYPE);
            panicDetails = intent.getParcelableExtra(PANIC_DETAILS_KEY);

            keys = intent.getParcelableExtra(PANIC_KEYS);
            switch (action) {
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    Toast.makeText(getApplicationContext(), "Foreground service is started!", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    Toast.makeText(getApplicationContext(), "Foreground service is stopped!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onStartCommand(intent, flags, startId);
        }


        return Service.START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        getLocation();
        return mBinder;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                // Log.e(“Network-GPS”, “Disable”);
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    panicNetworkRequest(new PanicData(keys,panicDetails,userType));
                    sendMessageToUi(String.valueOf(latitude), String.valueOf(longitude));
                    // Log.e(“Network”, “Network”);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            panicNetworkRequest(new PanicData(keys,panicDetails,userType));
                            sendMessageToUi(String.valueOf(latitude), String.valueOf(longitude));
                        }
                    }
                } else
                    if (isGPSEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        panicNetworkRequest(new PanicData(keys,panicDetails,userType));
                        sendMessageToUi(String.valueOf(latitude), String.valueOf(longitude));
                        if (location == null) {

                            //Log.e(“GPS Enabled”, “GPS Enabled”);
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation   (LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    Toast.makeText(getApplicationContext(), "The latitude is " + latitude + " and longitude is" + longitude, Toast.LENGTH_SHORT).show();
                                    panicNetworkRequest(new PanicData(keys,panicDetails,userType));

                                    sendMessageToUi(String.valueOf(latitude), String.valueOf(longitude));
                                }
                            }
                        }
                    }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to enable?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        alertDialog.show();
    }

    private void sendMessageToUi(String latitude, String longitude) {
        Log.d("SendMessageToUI", "Sending information");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void panicNetworkRequest(PanicData data) {
        ApiService apiService;
        apiService = Client.getClient().create(ApiService.class);
        Call<PanicResponse> call = apiService.postLocation(data);
        call.enqueue(new Callback<PanicResponse>() {
            @Override
            public void onResponse(Call<PanicResponse> call, Response<PanicResponse> response) {
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getTerminate_panic()) {
                    stopForegroundService();

                }
                else if (response.body().getResponse() != null) {
                    switch (response.body().getResponse()) {
                        case "success":
                            Log.d("panicresponse", "success");
                            startForegroundService();
                            break;
                        case "failed":
                            Log.d("panicresponse", "failed");

                            break;
                        default:
                    }

                }


            }

            @Override
            public void onFailure(Call<PanicResponse> call, Throwable t) {


            }
        });


    }

    public class MyBinder extends Binder {
        LocationFinder getService() {
            return LocationFinder.this;
        }
    }

    private void startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
String channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel = createChannel();
        else {
            channel = "";
        }
        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel);

        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Sending location updates to server");
        bigTextStyle.bigText("Getting current location...");
        // Set big text style.
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resolute);
        builder.setLargeIcon(largeIconBitmap);
        // Make the notification max priority.
        builder.setPriority(Notification.PRIORITY_MAX);
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true);

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);
        getLocation();
    }

    private void stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    @NonNull
    @TargetApi(26)
    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "snap map fake location ";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel("snap map channel", name, importance);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return "snap map channel";
    }

}
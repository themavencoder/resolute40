package com.aloine.resolute40.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.panicalert.dialog.PanicDialog;
import com.aloine.resolute40.panicalert.model.Keys;
import com.aloine.resolute40.panicalert.model.PanicData;
import com.aloine.resolute40.panicalert.model.PanicDetails;
import com.aloine.resolute40.panicalert.model.PanicResponse;
import com.aloine.resolute40.panicalert.network.ApiService;
import com.aloine.resolute40.smartLocation.StartMappingActivity;
import com.aloine.resolute40.viewmap.activity.ViewMapActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private PanicDialog panicDialog;
    private CoordinatorLayout mCoordinatorLayout;
    private Boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstance app = AppInstance.getInstance();
        if (app.getUsertype().toLowerCase().equals("herdsman")) {
            setContentView(R.layout.herdsmen_dashboard);
            b = false;
        } else {
            setContentView(R.layout.dashboard);
            b = true;
        }


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

    }

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
                                panicDialog.setCancelable(false);
                                panicDialog.show(getSupportFragmentManager(), "my_dialog");
                                AppInstance app = AppInstance.getInstance();
                                Keys keys = new Keys(app.getClient_token(), app.getSession_token());
                                PanicDetails panicDetails = new PanicDetails(app.getUsername(), "A panic has been sent", "True", 0.0f, 0.0f);
                                PanicData panicData = new PanicData(keys, panicDetails, app.getUsertype());
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
                                Keys keys = new Keys(app.getClient_token(), app.getSession_token());
                                PanicDetails panicDetails = new PanicDetails(app.getUsername(), "A panic has been sent", "True", 0.0f, 0.0f);
                                PanicData panicData = new PanicData(keys, panicDetails, app.getUsertype());
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
                            Snackbar snackbar = success("Your panic was sent to the server");

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
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }

    private Snackbar success(String s) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }

}

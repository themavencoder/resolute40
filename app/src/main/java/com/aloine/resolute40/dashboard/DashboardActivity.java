package com.aloine.resolute40.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.Toast;

import com.aloine.resolute40.R;
import com.aloine.resolute40.map.MapsActivity;
import com.aloine.resolute40.smartLocation.StartMappingActivity;


public class DashboardActivity extends AppCompatActivity {
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        hideNavigationBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        init();
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

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
    }

    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (finalI) {
                        case 0:
                            Toast.makeText(DashboardActivity.this, "Map your farm is clicked", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DashboardActivity.this, StartMappingActivity.class));
                            break;
                        case 1:
                            Toast.makeText(DashboardActivity.this, "Activate panic button", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(DashboardActivity.this, "View your map farm", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(DashboardActivity.this, "Vet Aid", Toast.LENGTH_SHORT).show();
                            break;
                        default:
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
}

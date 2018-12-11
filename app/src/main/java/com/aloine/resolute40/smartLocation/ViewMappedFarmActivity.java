package com.aloine.resolute40.smartLocation;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.aloine.resolute40.R;
import com.aloine.resolute40.dashboard.DashboardActivity;
import com.aloine.resolute40.viewmap.ViewMapActivity;

public class ViewMappedFarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mapped_farm);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
    }

    public void viewMap(View view) {
        startActivity(new Intent(this, ViewMapActivity.class));
    }
}

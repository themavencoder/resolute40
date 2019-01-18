package com.aloine.resolute40;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.aloine.resolute40.auth.quickLogin.activity.SignInActivity;
import com.aloine.resolute40.auth.register.activity.RegisterActivity;

import static com.aloine.resolute40.auth.register.activity.RegisterActivity.mypreference;
import static com.aloine.resolute40.auth.register.activity.RegisterActivity.userName;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        screenDisplay();

        /*sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.contains(userName)) {
                    Intent intent1 = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent1);

                } else {
                    Intent intent2 = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent2);
                }
            }
        }, SPLASH_TIME_OUT);*/
    }
    private void screenDisplay() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS


        );
    }


    public void signIn(View view) {
        Intent intent1 = new Intent(SplashScreenActivity.this, SignInActivity.class);
        startActivity(intent1);

    }

    public void register(View view) {
        Intent intent2 = new Intent(SplashScreenActivity.this, RegisterActivity.class);
        startActivity(intent2);
    }
}

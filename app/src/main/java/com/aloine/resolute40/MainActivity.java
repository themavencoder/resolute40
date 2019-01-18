package com.aloine.resolute40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.aloine.resolute40.auth.register.activity.RegisterActivity;
import com.aloine.resolute40.auth.quickLogin.activity.SignInActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButtonSignIn, mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void init() {
        mButtonRegister =findViewById(R.id.button_register);
        mButtonRegister.setOnClickListener(this);
        mButtonSignIn = findViewById(R.id.btn_sign_in);
        mButtonSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_sign_in:
                startActivity(new Intent(this, SignInActivity.class));
                break;
                default:
                    //do nothing
        }



    }
}

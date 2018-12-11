package com.aloine.resolute40.auth.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.aloine.resolute40.R;

public class SignInActivity extends AppCompatActivity {
    private EditText mOne, mTwo, mThree, mFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        screenDisplay();
        init();
        setTransform();
    }

    private void setTransform() {

    }

    private void init() {
        mOne = findViewById(R.id.edit_pass_one);
        mTwo = findViewById(R.id.edit_pass_two);
        mThree = findViewById(R.id.edit_pass_three);
        mFour = findViewById(R.id.edit_pass_four);

        mOne.setTransformationMethod(null);
        mTwo.setTransformationMethod(null);
        mThree.setTransformationMethod(null);
        mFour.setTransformationMethod(null);
    }

    private void screenDisplay() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION


        );
    }
}

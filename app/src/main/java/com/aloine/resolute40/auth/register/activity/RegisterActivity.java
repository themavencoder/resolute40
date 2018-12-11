package com.aloine.resolute40.auth.register.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.login.SignInActivity;
import com.aloine.resolute40.auth.register.contract.RegisterContract;
import com.aloine.resolute40.auth.register.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private EditText mEtName, mEtPhone, mEtCommunity, mEtPin;
    private TextView mTvSignIn;
    private Button mButtonReg;
    private RegisterContract.Presenter mPresenter;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        flags();
        init();
        register();
        mPresenter = new RegisterPresenter(this);
        signIn();

    }

    private void signIn() {
        mTvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loadSignInScreen();

            }
        });
    }


    private void register() {
        mButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = mEtName.getText().toString();
                String community = mEtCommunity.getText().toString();
                String phone_number = mEtPhone.getText().toString();
                String pin = mEtPin.getText().toString();
                mPresenter.insertData(full_name,community,phone_number,pin);
                if (mPresenter.verifyEntries()) {
                    mPresenter.loadNextScreen();
                }
                else {
                    Snackbar snackbar = Snackbar.make(mCoordinatorLayout,"Some details are missing!",Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));


                    snackbar.show();
                    }





            }
        });
    }

    private void flags() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        );
    }

    private void init() {
        mEtName = findViewById(R.id.edit_full_name);
        mEtPhone = findViewById(R.id.edit_phone_number);
        mEtCommunity = findViewById(R.id.edit_community);
        mEtPin = findViewById(R.id.edit_pin);
        mTvSignIn = findViewById(R.id.text_sign_in);
        mButtonReg = findViewById(R.id.button_register);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
    }


    @Override
    public void navigateToNextScreen() {
        startActivity(new Intent(RegisterActivity.this,SignInActivity.class));

    }

    @Override
    public void navigateToSignInScreen() {
        startActivity(new Intent(RegisterActivity.this,SignInActivity.class));
    }
}

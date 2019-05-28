package com.aloine.resolute40.auth.formalLogin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.formalLogin.contract.FormalContract;
import com.aloine.resolute40.auth.formalLogin.presenter.FormalPresenter;
import com.aloine.resolute40.auth.quickLogin.activity.SignInActivity;
import com.aloine.resolute40.auth.quickLogin.dialog.LoginDialog;
import com.aloine.resolute40.auth.quickLogin.model.LoginModel;
import com.aloine.resolute40.auth.quickLogin.network.ApiService;
import com.aloine.resolute40.auth.quickLogin.network.LoginResponse;
import com.aloine.resolute40.auth.register.activity.RegisterActivity;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aloine.resolute40.auth.register.activity.RegisterActivity.mypreference;
import static com.aloine.resolute40.auth.register.activity.RegisterActivity.userName;

public class FormalSignInActivity extends AppCompatActivity implements FormalContract.View{
    private EditText mEtPhone, mEtPin;
    private Button mButSignIn;
    private FormalContract.Presenter presenter;
    private CoordinatorLayout mCoordinatorLayout;
    private LoginDialog mLoginDialog;
    private ApiService mApiService;
    private LoginModel model;
    private Farmer farmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formal_sign_in);
        farmer = new Farmer();
        statusColor();
        flags();
        init();
        signIn();



    }

    private void signIn() {
        mButSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.insertData(mEtPhone.getText().toString(),mEtPin.getText().toString());
                if (presenter.isValid()) {
                    model = new LoginModel(mEtPhone.getText().toString(),mEtPin.getText().toString());
                    mLoginDialog.setCancelable(false);
                    mLoginDialog.show(getSupportFragmentManager(), "my_dialog");
                    performNetworkLogin(model);


                    Toast.makeText(FormalSignInActivity.this, "Valid toast", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar snackbar = inCompleteDetails();
                    snackbar.show();
                }

            }
        });
    }

    private Snackbar inCompleteDetails() {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout,"Fill your information correctly",Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));

        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)sbView.getLayoutParams();
        params.gravity = Gravity.TOP;
        sbView.setLayoutParams(params);

        return snackbar;

    }

    private void init() {
        mEtPhone = findViewById(R.id.edit_phone_number);
        mEtPin = findViewById(R.id.edit_pin);
        mButSignIn = findViewById(R.id.button_sign_in);
        presenter = new FormalPresenter(this);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        mLoginDialog = new LoginDialog();
    }

    private void flags() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        );
    }

    private void statusColor() {
        Window window = this.getWindow();
        if(Build.VERSION.SDK_INT >= 21)
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(this,DashboardActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void navigateToPreviousScreen() {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void performNetworkLogin(final LoginModel model) {
        mApiService = Client.getClient().create(ApiService.class);

        Call<LoginResponse> call = mApiService.startLogin(model);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.body().getResponse()) {
                    case "success":
                        mLoginDialog.dismiss();
                        farmer.setId(1);
                        farmer.setPhone_number(mEtPhone.getText().toString());
                        farmer.save();
                        AppInstance app = AppInstance.getInstance();
                        app.setUsername(mEtPhone.getText().toString());
                        app.setClient_token(response.body().getAuth_keys().getClient_token());
                        app.setSession_token(response.body().getAuth_keys().getSession_token());
                        app.setUsertype(response.body().getUser_type());

                        // Toast toast = Toast.makeText(SignInActivity.this,"The session token is " + app.getSession_token() + "and client token is " + app.getClient_token(),Toast.LENGTH_LONG);
                        // toast.setGravity(Gravity.CENTER,0,0);
                        //  toast.show();
                        startActivity(new Intent(FormalSignInActivity.this, DashboardActivity.class));
                        Toast.makeText(FormalSignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case "failed":
                        mLoginDialog.dismiss();
                        Toast.makeText(FormalSignInActivity.this, "Wrong password. Please try again!", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }



            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mLoginDialog.dismiss();
                internetError(model);
                Toast toast = Toast.makeText(FormalSignInActivity.this,"Internet connection error. Try again!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });


    }
    private void internetError(final LoginModel model) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Unable to connect to the internet", Snackbar.LENGTH_LONG)
                .setAction("TRY AGAIN", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performNetworkLogin(model);
                    }


                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)sbView.getLayoutParams();
        params.gravity = Gravity.TOP;
        sbView.setLayoutParams(params);
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
    }

            }

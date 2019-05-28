package com.aloine.resolute40.auth.quickLogin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.formalLogin.activity.FormalSignInActivity;
import com.aloine.resolute40.auth.quickLogin.contract.LoginContract;
import com.aloine.resolute40.auth.quickLogin.dialog.LoginDialog;
import com.aloine.resolute40.auth.quickLogin.model.LoginModel;
import com.aloine.resolute40.auth.quickLogin.network.ApiService;
import com.aloine.resolute40.auth.quickLogin.network.LoginResponse;
import com.aloine.resolute40.auth.quickLogin.presenter.LoginPresenter;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.database.table.Farmer_Table;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.dashboard.DashboardActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aloine.resolute40.auth.register.activity.RegisterActivity.mypreference;
import static com.aloine.resolute40.auth.register.activity.RegisterActivity.userName;

public class SignInActivity extends AppCompatActivity implements LoginContract.View {
    private EditText mEtOne, mEtTwo, mEtThree, mEtFour;
    private LoginContract.Presenter mPresenter;
    private LoginDialog mLoginDialog;
    private ApiService mApiService;
    private LoginModel model;
    private CoordinatorLayout mCoordinatorLayout;
    private Farmer farmer;
    private String phone;
    private AppInstance app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        screenDisplay();
        statusColor();

        init();
        farmer = SQLite.select().from(Farmer.class).where(Farmer_Table.id.eq(1)).querySingle();
       SharedPreferences sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        if (sharedPreferences.contains(userName)) {
            app.setUsername(sharedPreferences.getString(userName,""));

        }

    }

    private void statusColor() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 21)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }


    private void init() {
        mEtOne = findViewById(R.id.edit_pass_one);
        mEtTwo = findViewById(R.id.edit_pass_two);
        mEtThree = findViewById(R.id.edit_pass_three);
        mEtFour = findViewById(R.id.edit_pass_four);
        mLoginDialog = new LoginDialog();
        mEtOne.setTransformationMethod(null);
        mEtTwo.setTransformationMethod(null);
        mEtThree.setTransformationMethod(null);
        mEtFour.setTransformationMethod(null);
        mEtOne.addTextChangedListener(watcher);
        mEtTwo.addTextChangedListener(watcher);
        mEtThree.addTextChangedListener(watcher);
        mEtFour.addTextChangedListener(watcher);
        mPresenter = new LoginPresenter(this);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        app = AppInstance.getInstance();

    }

    private void screenDisplay() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION


        );
    }

    @Override
    public void navigateToNextScreen() {
        startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            EditText text = (EditText) getCurrentFocus();
            if (text != null && text.length() > 0) {
                View next = text.focusSearch(View.FOCUS_RIGHT);
                if (next != null) {
                    next.requestFocus();
                }
            }

            if (!mEtOne.getText().toString().isEmpty() && !mEtTwo.getText().toString().isEmpty() && !mEtThree.getText().toString().isEmpty() && !mEtFour.getText().toString().isEmpty()) {
                mPresenter.savePins(mEtOne.getText().toString(), mEtTwo.getText().toString(), mEtThree.getText().toString(), mEtFour.getText().toString());
                mPresenter.concatPin();
                mLoginDialog.setCancelable(false);
                mLoginDialog.show(getSupportFragmentManager(), "my_dialog");
             //   Toast.makeText(SignInActivity.this, "shared pref" + app.getUsername(), Toast.LENGTH_SHORT).show();
                model = new LoginModel(farmer.getPhone_number(),mPresenter.concatPin());
                Toast.makeText(SignInActivity.this, farmer.getPhone_number(), Toast.LENGTH_SHORT).show();
                performNetworkLogin(model);


            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void performNetworkLogin(final LoginModel model) {
        mApiService = Client.getClient().create(ApiService.class);

        Call<LoginResponse> call = mApiService.startLogin(model);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.body().getResponse()) {
                    case "success":
                        mLoginDialog.dismiss();
                        AppInstance app = AppInstance.getInstance();
                        app.setClient_token(response.body().getAuth_keys().getClient_token());
                        app.setSession_token(response.body().getAuth_keys().getSession_token());
                        app.setUsertype(response.body().getUser_type());
                        app.setUsername(farmer.getPhone_number());
                       // Toast toast = Toast.makeText(SignInActivity.this,"The session token is " + app.getSession_token() + "and client token is " + app.getClient_token(),Toast.LENGTH_LONG);
                       // toast.setGravity(Gravity.CENTER,0,0);
                      //  toast.show();
                        startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
                        Toast.makeText(SignInActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                        break;
                    case "failed":
                        mLoginDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Wrong password. Please try again!", Toast.LENGTH_SHORT).show();
                        break;
                        default:

                }



            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mLoginDialog.dismiss();
                internetError(model);
                Toast toast = Toast.makeText(SignInActivity.this,"Internet connection error. Try again!",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });

    }

    private void wrongPin(final LoginModel model) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Your pin is not correct!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
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
        sbView.setBackgroundColor(Color.RED);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
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

    public void textviewSignIn(View view) {
        Intent intent = new Intent(this,FormalSignInActivity.class);
        startActivity(intent);
    }
}

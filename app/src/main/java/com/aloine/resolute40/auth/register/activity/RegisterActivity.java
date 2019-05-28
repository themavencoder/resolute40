package com.aloine.resolute40.auth.register.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.formalLogin.activity.FormalSignInActivity;
import com.aloine.resolute40.auth.register.contract.RegisterContract;
import com.aloine.resolute40.auth.register.database.table.Farmer;
import com.aloine.resolute40.auth.register.model.RegisterModel;
import com.aloine.resolute40.auth.register.network.ApiService;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.auth.register.network.RegisterResponse;
import com.aloine.resolute40.auth.register.presenter.RegisterPresenter;
import com.aloine.resolute40.auth.register.registerdialog.DialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private EditText mEtName, mEtPhone, mEtCommunity, mEtPin, mEtProductType;
    private Spinner mSpState;
    private TextView mTvSignIn;
    private Button mButtonReg;
    private RegisterContract.Presenter mPresenter;
    private CoordinatorLayout mCoordinatorLayout;
    private ApiService mApiService;
    private DialogFragment dialogFragment;
    private RegisterModel model;
    private Farmer farmer;
    public SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String userName = "userNameKey";
    public AppInstance app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        flags();
        if (Build.VERSION.SDK_INT >= 21) {
            statusColor();
        }


        init();
        register();
        mPresenter = new RegisterPresenter(this);
        signIn();
        farmer = new Farmer();
        app = AppInstance.getInstance();
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(userName)) {
            app.setUsername(sharedPreferences.getString(userName,""));

        }



    }

    private void statusColor() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        }
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
                String productType = mEtProductType.getText().toString();
                mPresenter.insertData(full_name, phone_number,community, pin,productType);

                if (mPresenter.verifyEntries() && !mSpState.getSelectedItem().toString().equals("Select state")) {
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(getSupportFragmentManager(), "my_dialog");
                     model = new RegisterModel(full_name, mSpState.getSelectedItem().toString(),phone_number,community, productType,pin, "null", "Farmer" );

                    sendUserData(model);

                } else {

                    Snackbar snackbar = inCompleteDetails();
                    snackbar.show();

                }


            }
        });
    }

    @NonNull
    private Snackbar inCompleteDetails() {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Some details are missing!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }

    private void flags() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        );
    }

    private void init() {
        mSpState = findViewById(R.id.spin_status);
        mSpState.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mEtName = findViewById(R.id.edit_full_name);
        mEtPhone = findViewById(R.id.edit_phone_number);
        mEtCommunity = findViewById(R.id.edit_community);
        mEtPin = findViewById(R.id.edit_pin);
        mEtProductType = findViewById(R.id.edit_product_type);
        mTvSignIn = findViewById(R.id.text_sign_in);
        mButtonReg = findViewById(R.id.button_register);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        dialogFragment = new DialogFragment();
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.Status, R.layout.spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpState.setAdapter(statusAdapter);
    }


    @Override
    public void navigateToNextScreen() {
        startActivity(new Intent(RegisterActivity.this, FormalSignInActivity.class));

    }

    @Override
    public void navigateToSignInScreen() {
        startActivity(new Intent(RegisterActivity.this, FormalSignInActivity.class));
    }

    private void sendUserData(RegisterModel registerModel) {
        mApiService = Client.getClient().create(ApiService.class);
        Call<RegisterResponse> call = mApiService.registerUser(registerModel);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body().getResponse().equals("success")) {
                    dialogFragment.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(userName,mEtPhone.getText().toString());
                    editor.commit();
                    farmer.setId(1);
                    farmer.setPhone_number(mEtPhone.getText().toString());
                    farmer.save();
                    app.setUsername(mEtPhone.getText().toString());
                    Toast.makeText(RegisterActivity.this, "Registration status:" + response.body().getResponse(), Toast.LENGTH_SHORT).show();
                    mPresenter.loadNextScreen();
                }
                if (response.body().getResponse().equals("Failure")) {
                    Toast.makeText(RegisterActivity.this, "Registration status:" + response.body().getResponse(), Toast.LENGTH_SHORT).show();

                    registerFailure();

                }

                if (response.body().getMessage().equals("Phone number exists")) {
                    dialogFragment.dismiss();
                    Toast.makeText(RegisterActivity.this, "This phone number has been registered by another user.", Toast.LENGTH_SHORT).show();

                    return;
                }
                else {
                    dialogFragment.dismiss();

                    Toast.makeText(RegisterActivity.this, "Registration status:" + response.body().getResponse(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                dialogFragment.dismiss();
                Snackbar snackbar = errorFailure("Unable to connect to the server");
                snackbar.show();
            }

        });

    }

    @NonNull
    private Snackbar errorFailure(String s) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_LONG)
                .setAction("TRY AGAIN", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mPresenter.verifyEntries()) {
                            dialogFragment.setCancelable(false);
                            dialogFragment.show(getSupportFragmentManager(), "my_dialog");
                            sendUserData(model);

                        }

                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return snackbar;
    }

    private void registerFailure() {
        Snackbar snackbar = errorFailure("Unable to process registration, Try again");
        snackbar.show();
    }
}

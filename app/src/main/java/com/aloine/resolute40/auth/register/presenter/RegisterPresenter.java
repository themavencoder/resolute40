package com.aloine.resolute40.auth.register.presenter;

import android.widget.Toast;

import com.aloine.resolute40.auth.register.contract.RegisterContract;
import com.aloine.resolute40.auth.register.model.RegisterModel;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private RegisterModel model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        model = new RegisterModel();

    }

    @Override
    public void loadNextScreen() {
        view.navigateToNextScreen();

    }

    @Override
    public void loadSignInScreen() {
        view.navigateToSignInScreen();

    }

    @Override
    public boolean verifyEntries() {
        if (!model.getCommunity().isEmpty() && !model.getFull_name().isEmpty() && model.getPhone_number().length() == 11 && model.getPin().length() == 4) {

            return true;
        }


        return false;
    }

    @Override
    public void insertData(String fullName, String phone, String community, String pin) {
        model.setFull_name(fullName);
        model.setPhone_number(phone);
        model.setCommunity(community);
        model.setPin(pin);
    }
}

package com.aloine.resolute40.auth.formalLogin.presenter;

import com.aloine.resolute40.auth.formalLogin.contract.FormalContract;
import com.aloine.resolute40.auth.formalLogin.model.FormalModel;

public class FormalPresenter implements FormalContract.Presenter {
    private FormalContract.View view;
    private FormalModel model;

    public FormalPresenter(FormalContract.View view) {
        model = new FormalModel();
        this.view = view;
    }

    @Override
    public void loadNextScreen() {
        view.navigateToNextScreen();

    }

    @Override
    public void loadPreviousScreen() {
        view.navigateToPreviousScreen();

    }

    @Override
    public boolean isValid() {
        if (model.getPhoneNumber().length() == 11 && model.getPin().length() == 4) {
            return true;
        }
        return false;
    }

    @Override
    public void insertData(String phone, String pin) {
        model.setPhoneNumber(phone);
        model.setPin(pin);

    }
}

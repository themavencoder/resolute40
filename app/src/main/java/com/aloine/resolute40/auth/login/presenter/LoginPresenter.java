package com.aloine.resolute40.auth.login.presenter;

import com.aloine.resolute40.auth.login.contract.LoginContract;
import com.aloine.resolute40.auth.login.model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginModel model;

    public LoginPresenter(LoginContract.View view) {

        this.view = view;
        model = new LoginModel();
    }
    @Override
    public void loadNextScreen() {
        view.navigateToNextScreen();

    }

    @Override
    public void savePins(String one, String two, String three, String four) {
    model.setPinOne(one);
    model.setPinTwo(two);
    model.setPinThree(three);
    model.setPinFour(four);


    }

    @Override
    public String concatPin() {
        String concatPins = model.getPinOne() + model.getPinTwo() + model.getPinThree() + model.getPinFour();
        return concatPins;
    }
}

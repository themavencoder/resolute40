package com.aloine.resolute40.auth.login.contract;

public interface LoginContract {

    interface View {
        void navigateToNextScreen();
    }
    interface Presenter {
        void loadNextScreen();
        void savePins(String one, String two, String three, String four);
        String concatPin();
    }
}

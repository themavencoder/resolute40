package com.aloine.resolute40.auth.formalLogin.contract;

public interface FormalContract {

    interface View {
        void navigateToNextScreen();
        void navigateToPreviousScreen();


    }
    interface Presenter {
        void loadNextScreen();
        void loadPreviousScreen();
        boolean isValid();
        void insertData(String phone, String pin);
    }
}

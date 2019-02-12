package com.aloine.resolute40.auth.register.contract;

public interface RegisterContract {

    interface View {
        void navigateToNextScreen();
        void navigateToSignInScreen();


    }

    interface Presenter {
        void loadNextScreen();
        void loadSignInScreen();
        boolean verifyEntries();
        void insertData(String fullName, String phone, String community, String pin, String productType);

    }
}

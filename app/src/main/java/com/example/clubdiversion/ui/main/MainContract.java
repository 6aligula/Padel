package com.example.clubdiversion.ui.main;

public interface MainContract {
    interface View {
        void showWelcomeMessage(String message);
        void navigateToLogin();
        void navigateToInstalaciones();
        void closeApp();
    }

    interface Presenter {
        void checkUser();
        void logout();
    }
}

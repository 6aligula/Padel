package com.example.clubdiversion.ui.main;

public interface MainContract {
    interface View {
        void showWelcomeMessage(String message);
        void navigateToLogin();
        void navigateToInstalaciones();
        void navigateToAdmin();
        void navigateToProfile();
        void closeApp();
        void setAdminMenuVisibility(boolean isVisible);
    }

    interface Presenter {
        void checkUser();
        void logout();
    }
}

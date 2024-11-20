package com.example.clubdiversion.ui.login;

public interface LoginContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showLoginSuccess();
        void showLoginError(String message);
        void navigateToRegister();
        void navigateToHome();
    }

    interface Presenter {
        void doLogin(String username, String password);
    }
}

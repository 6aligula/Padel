package com.example.clubdiversion.ui.register;

public interface RegisterContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showRegisterSuccess(String message);
        void showRegisterError(String error);
        void navigateToLogin();
    }

    interface Presenter {
        void doRegister(String nip, String nombre, String direccion, String telefono, String password, String confirmPassword);
    }
}

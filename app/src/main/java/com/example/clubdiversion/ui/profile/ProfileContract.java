package com.example.clubdiversion.ui.profile;

public interface ProfileContract {
    interface View {
        void showProfile(String name, String email, String phone, String address);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadProfile();
    }
}

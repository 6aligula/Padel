package com.example.clubdiversion.ui.profile;

import com.example.clubdiversion.data.entities.ReservationResponse;

import java.util.List;

public interface ProfileContract {
    interface View {
        void showProfile(String name, String email, String phone, String address);
        void showReservations(List<ReservationResponse> reservations);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadProfile();
        void loadReservations(); // Metodo para cargar las reservas

        void refreshUserProfile();
    }
}

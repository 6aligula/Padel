package com.example.clubdiversion.ui.reservaciones;

import java.util.List;

public interface ReservacionesContract {

    interface View {
        void showDate(String date);
        void showReservationSuccess();
        void showReservationError(String message);
        void showReservationsList(List<String> reservations);
        void showUserName(String name);
        void showUnknownUser();
        void showDateError();

    }

    interface Presenter {
        void onDateSelected(int year, int month, int day);
        void onAcceptReservation(String date, int reservationNumber);
        void onListReservations();
        void loadUserName();
        void showDatePicker();
    }
}

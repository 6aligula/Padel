package com.example.clubdiversion.ui.reservaciones;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.clubdiversion.data.entities.ReservationRequest;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.repository.ReservacionesRepository;
import com.example.clubdiversion.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservacionesPresenter implements ReservacionesContract.Presenter {

    private final ReservacionesContract.View view;
    private final ReservacionesRepository repository;

    public ReservacionesPresenter(ReservacionesContract.View view, Context context) {
        this.view = view;
        this.repository = new ReservacionesRepository(context);
    }

    @Override
    public void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                (Context) view, // Contexto de la Vista
                (view, yearSelected, monthSelected, daySelected) -> {
                    // Lógica al seleccionar la fecha
                    onDateSelected(yearSelected, monthSelected, daySelected);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSelected(int year, int month, int day) {
        Calendar current = Calendar.getInstance();
        long selectedDate = year * 10000 + month * 100 + day;
        long currentDate = current.get(Calendar.YEAR) * 10000 + current.get(Calendar.MONTH) * 100 + current.get(Calendar.DAY_OF_MONTH);

        if (selectedDate < currentDate) {
            view.showDateError();
        } else {
            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
            view.showDate(formattedDate);
        }
    }

    @Override
    public void onAcceptReservation(String date, int reservationNumber) {
        if (date.equals("Pulse aquí para la fecha")) {
            view.showDateError();
            return;
        }

        if (repository.isReservationTaken(date, reservationNumber)) {
            view.showReservationError("Instalación ya reservada");
            return;
        }

        // Obtén el token desde UserRepository
        UserRepository userRepository = new UserRepository((Context) view);
        String token = userRepository.getToken();

        if (token == null) {
            view.showReservationError("Token no encontrado. Por favor, inicia sesión.");
            return;
        }

        // Crea el objeto de la reserva
        ReservationRequest request = new ReservationRequest(reservationNumber, date);

        // Llama al repositorio para enviar la reserva
        repository.addReservationToBackend(request, token, new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showReservationSuccess();
                    repository.markReservationAsSynced(request);
                } else {
                    view.showReservationError("Error al realizar la reserva: Fecha no disponible");
                    //view.showReservationError("Error al realizar la reserva: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                view.showReservationError("Error de red al realizar la reserva: " + t.getMessage());
            }
        });
    }

    @Override
    public void onListReservations() {
        List<String> reservations = repository.getAllReservations();
        if (reservations.isEmpty()) {
            view.showReservationError("No hay reservas");
        } else {
            view.showReservationsList(reservations);
        }
    }

    @Override
    public void loadUserName() {
        String userName = repository.getUserName();
        if (userName == null) {
            view.showUnknownUser();
        } else {
            view.showUserName(userName);
        }
    }
}

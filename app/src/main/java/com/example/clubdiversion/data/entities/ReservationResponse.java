package com.example.clubdiversion.data.entities;

public class ReservationResponse {
    private int id; // ID de la reserva en el backend
    private String message; // Mensaje del servidor

    public ReservationResponse(int id, String message) {
        this.id = id;
        this.message = message;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.clubdiversion.data.entities;

public class ReservationResponse {
    private int id; // ID de la reserva en el backend
    private String message; // Mensaje del servidor (usado al añadir una reserva)
    private String user; // Usuario asociado a la reserva (usado al obtener reservas)
    private String date; // Fecha de la reserva
    private String installation; // Instalación reservada
    private boolean is_synced; // Sincronización con el backend
    private String created_at; // Fecha de creación
    private String updated_at; // Fecha de actualización

    // Constructor para añadir una reserva
    public ReservationResponse(int id, String message) {
        this.id = id;
        this.message = message;
    }

    // Constructor para obtener todas las reservas
    public ReservationResponse(int id, String user, String date, String installation, boolean is_synced, String created_at, String updated_at) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.installation = installation;
        this.is_synced = is_synced;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInstallation() {
        return installation;
    }

    public void setInstallation(String installation) {
        this.installation = installation;
    }

    public boolean isSynced() {
        return is_synced;
    }

    public void setSynced(boolean is_synced) {
        this.is_synced = is_synced;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}

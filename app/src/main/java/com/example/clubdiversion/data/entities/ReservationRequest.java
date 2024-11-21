package com.example.clubdiversion.data.entities;

public class ReservationRequest {
    private int installation_id; // Cambia el nombre aquí
    private String date;

    public ReservationRequest(int installation_id, String date) {
        this.installation_id = installation_id;
        this.date = date;
    }

    public int getInstallation_id() {
        return installation_id;
    }

    public void setInstallation_id(int installation_id) {
        this.installation_id = installation_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "installation_id=" + installation_id +
                ", date='" + date + '\'' +
                '}';
    }
}

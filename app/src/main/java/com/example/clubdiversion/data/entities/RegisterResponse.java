package com.example.clubdiversion.data.entities;

public class RegisterResponse {
    private String token;
    private String username;
    private String email;
    private boolean isAdmin; // Asegúrate de que este campo coincide con la respuesta JSON


    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public boolean getIsAdmin() {
        return isAdmin; // Getter para isAdmin
    }
}


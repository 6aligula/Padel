package com.example.clubdiversion.data.entities;

public class RegisterRequest {
    private String nip;
    private String name;
    private String direccion;
    private String telefono;
    private String password;

    public RegisterRequest(String nip, String name, String direccion, String telefono, String password) {
        this.nip = nip;
        this.name = name;
        this.direccion = direccion;
        this.telefono = telefono;
        this.password = password;
    }

    // Getters y Setters (opcional)
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


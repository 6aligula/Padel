package com.example.clubdiversion.data.entities;

public class UserResponse {
    private int id;
    private String username;
    private String name;
    private String direccion;
    private String telefono;
    private boolean isAdmin;
    private String password; // Campo adicional para la contraseña


    // Constructor
    public UserResponse(int id, String username, String name, String direccion, String telefono, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.direccion = direccion;
        this.telefono = telefono;
        this.isAdmin = isAdmin;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

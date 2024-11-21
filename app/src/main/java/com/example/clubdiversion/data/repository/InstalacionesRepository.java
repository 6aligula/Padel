package com.example.clubdiversion.data.repository;

import android.content.Context;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;

public class InstalacionesRepository {

    private final ConexionSQLiteHelper dbHelper;
    private final UserRepository userRepository;

    public InstalacionesRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
        this.userRepository = new UserRepository(context);
    }

    // Método para verificar si el usuario es administrador
    public boolean isCurrentUserAdmin() {
        return userRepository.isCurrentUserAdmin();
    }

    // Método para obtener capacidades de las instalaciones
    public String[] getCapacities() {
        return new String[]{"", "40", "55", "60", "7", "14", "19", "12", "26", "7"};
    }

    // Método para obtener estados de techado
    public String[] getTechadoStates() {
        return new String[]{"", "SI", "SI", "SI", "SI", "NO", "NO", "NO", "NO", "NO"};
    }

    // Método para obtener áreas de las instalaciones
    public String[] getAreas() {
        return new String[]{"", "80", "150", "220", "40", "45", "80", "50", "30", "40"};
    }
}

package com.example.clubdiversion.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;
import com.example.clubdiversion.data.entities.ReservationRequest;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservacionesRepository {

    private final ConexionSQLiteHelper dbHelper;
    private final ApiService apiService;

    public ReservacionesRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
        this.apiService = RetrofitClient.getApiService();
    }

    public boolean isReservationTaken(String date, int reservationNumber) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_INST + " WHERE " +
                DatabaseSchema.INST_FECHA + " = ? AND " +
                DatabaseSchema.INST_ID_INST + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date, String.valueOf(reservationNumber)});

        boolean isTaken = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isTaken;
    }

    public void addReservation(String date, int reservationNumber, boolean isSynced) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.INST_FECHA, date);
        values.put(DatabaseSchema.INST_ID_INST, reservationNumber);
        values.put(DatabaseSchema.INST_SYNCED, isSynced ? 1 : 0); // 1 para sincronizado, 0 para no
        db.insert(DatabaseSchema.T_INST, null, values);
        db.close();
    }

    public List<String> getAllReservations() {
        List<String> reservations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_INST;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                reservations.add("ID: " + cursor.getInt(0) +
                        ", Fecha: " + cursor.getString(1) +
                        ", Instalación: " + cursor.getString(2) +
                        ", Synced: " + (cursor.getInt(3) == 1 ? "Yes" : "No"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reservations;
    }

    public String getUserName() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " + DatabaseSchema.CAMPO_NAME + " FROM " + DatabaseSchema.TABLA_USERS + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return userName;
    }

    public void addReservationToBackend(ReservationRequest reservationRequest, String token, Callback<ReservationResponse> callback) {
        // Registrar el contenido del token y los datos de la solicitud
        Log.d("ReservacionesRepository", "Enviando reserva al backend:");
        Log.d("ReservacionesRepository", "Token: " + token);
        Log.d("ReservacionesRepository", "ReservationRequest: " + reservationRequest.toString());

        // Realiza la llamada
        apiService.addReservation(reservationRequest, "Bearer " + token).enqueue(callback);
    }

    public void markReservationAsSynced(ReservationRequest reservation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.INST_SYNCED, 1);
        String whereClause = DatabaseSchema.INST_FECHA + " = ? AND " + DatabaseSchema.INST_ID_INST + " = ?";
        String[] whereArgs = {reservation.getDate(), String.valueOf(reservation.getInstallation_id())};
        db.update(DatabaseSchema.T_INST, values, whereClause, whereArgs);
        db.close();
    }
}

package com.example.clubdiversion.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class ReservacionesRepository {

    private final ConexionSQLiteHelper dbHelper;

    public ReservacionesRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
    }

    public boolean isReservationTaken(String date, int reservationNumber) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_INST + " WHERE " +
                DatabaseSchema.INST_FECHA + " = ? AND " +
                DatabaseSchema.INST_ID_INST + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date, String.valueOf(reservationNumber)});

        Log.d("ReservacionesRepository", "Consulta realizada: " + query +
                " | Fecha: " + date + " | Número de reserva: " + reservationNumber);

        boolean isTaken = cursor.moveToFirst();
        if (isTaken) {
            Log.d("ReservacionesRepository", "Reserva encontrada.");
        } else {
            Log.d("ReservacionesRepository", "No hay reserva para la fecha/número proporcionados.");
        }
        cursor.close();
        db.close();
        return isTaken;
    }

    public void addReservation(String date, int reservationNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.INST_FECHA, date);
        values.put(DatabaseSchema.INST_ID_INST, reservationNumber);
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
                        ", Instalación: " + cursor.getString(2));
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
}

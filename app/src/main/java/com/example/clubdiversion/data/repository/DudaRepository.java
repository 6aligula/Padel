package com.example.clubdiversion.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class DudaRepository {

    private final ConexionSQLiteHelper dbHelper;
    private final UserRepository userRepository;

    public DudaRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
        this.userRepository = new UserRepository(context);
    }

    public boolean isCurrentUserAdmin() {
        return userRepository.isCurrentUserAdmin();
    }

    public boolean isDudaRegistered(String duda) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Duda + " WHERE " + DatabaseSchema.DUDA_DUDA + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{duda});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public void insertDuda(String duda, String respuesta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.DUDA_DUDA, duda);
        values.put(DatabaseSchema.DUDA_RESP, respuesta);
        db.insert(DatabaseSchema.T_Duda, null, values);
        db.close();
    }

    public void updateDuda(String duda, String respuesta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.DUDA_RESP, respuesta);
        db.update(DatabaseSchema.T_Duda, values, DatabaseSchema.DUDA_DUDA + " = ?", new String[]{duda});
        db.close();
    }

    public List<String> getDudaList() {
        List<String> dudas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Duda;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                dudas.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.DUDA_DUDA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dudas;
    }

    public List<String> getResponseList() {
        List<String> responses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Duda;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                responses.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.DUDA_RESP)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return responses;
    }

}

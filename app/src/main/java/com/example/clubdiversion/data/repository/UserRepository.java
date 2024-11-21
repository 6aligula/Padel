package com.example.clubdiversion.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.SocioDB;

public class UserRepository {
    private final ConexionSQLiteHelper dbHelper;

    public UserRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
    }

    public static Long insertUser(ContentValues values, SQLiteDatabase db) {
        return db.insert(DatabaseSchema.TABLA_USERS, null, values);
    }

    public static Cursor getUsers(SQLiteDatabase db, String query) {
        return db.rawQuery(query, null);
    }

    public static int updateUser(String where, String[] args, ContentValues values, SQLiteDatabase db) {
        return db.update(DatabaseSchema.TABLA_USERS, values, where, args);
    }

    public static int deleteUser(String where, String[] args, SQLiteDatabase db) {
        return db.delete(DatabaseSchema.TABLA_USERS, where, args);
    }

    public boolean isCurrentUserAdmin() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean isAdmin = false;
        Cursor cursor = null;

        try {
            String query = "SELECT " + DatabaseSchema.CAMPO_IS_ADMIN + " FROM " + DatabaseSchema.TABLA_USERS + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                int isAdminValue = cursor.getInt(0); // CAMPO_IS_ADMIN
                isAdmin = isAdminValue == 1;
            }
        } catch (Exception e) {
            Log.e("UserRepository", "Error al verificar administrador: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return isAdmin;
    }

    public SocioDB getCurrentUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SocioDB usuario = null;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + DatabaseSchema.TABLA_USERS + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                usuario = new SocioDB(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_DIRECCION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_USERNAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.CAMPO_IS_ADMIN)) == 1
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return usuario;
    }
    public boolean logoutUser() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DatabaseSchema.TABLA_USERS, null, null);
        db.close();
        return result > 0;
    }
    // Metodo para insertar un usuario desde LoginResponse
    public boolean saveUser(LoginResponse loginResponse) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseSchema.CAMPO_ID, loginResponse.getId());
        values.put(DatabaseSchema.CAMPO_USERNAME, loginResponse.getUsername());
        values.put(DatabaseSchema.CAMPO_NAME, loginResponse.getName());
        values.put(DatabaseSchema.CAMPO_DIRECCION, loginResponse.getDireccion());
        values.put(DatabaseSchema.CAMPO_TELEFONO, loginResponse.getTelefono());
        values.put(DatabaseSchema.CAMPO_IS_ADMIN, loginResponse.isAdmin() ? 1 : 0);
        values.put(DatabaseSchema.CAMPO_TOKEN, loginResponse.getToken());

        long result = db.insert(DatabaseSchema.TABLA_USERS, null, values);
        db.close();

        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    public String getToken() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String token = null;
        Cursor cursor = null;

        try {
            String query = "SELECT " + DatabaseSchema.CAMPO_TOKEN + " FROM " + DatabaseSchema.TABLA_USERS + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                token = cursor.getString(0); // Recupera el token
            }
        } catch (Exception e) {
            Log.e("UserRepository", "Error al recuperar el token: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return token; // Devuelve el token o null si no existe
    }
}

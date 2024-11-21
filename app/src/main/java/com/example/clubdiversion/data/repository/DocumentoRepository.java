package com.example.clubdiversion.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clubdiversion.data.database.ConexionSQLiteHelper;
import com.example.clubdiversion.data.database.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class DocumentoRepository {

    private final ConexionSQLiteHelper dbHelper;
    private final UserRepository userRepository;

    public DocumentoRepository(Context context) {
        this.dbHelper = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
        this.userRepository = new UserRepository(context);
    }

    public boolean isCurrentUserAdmin() {
        return userRepository.isCurrentUserAdmin();
    }

    public boolean isDescriptionRegistered(String description) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Doc + " WHERE " + DatabaseSchema.DOC_DES + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{description});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public void insertDocument(String description, String link) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.DOC_DES, description);
        values.put(DatabaseSchema.DOC_LINK, link);
        db.insert(DatabaseSchema.T_Doc, null, values);
        db.close();
    }

    public List<String> getDescriptions() {
        List<String> descriptions = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Doc;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                descriptions.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.DOC_DES)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return descriptions;
    }

    public List<String> getLinks() {
        List<String> links = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseSchema.T_Doc;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                links.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.DOC_LINK)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return links;
    }
}

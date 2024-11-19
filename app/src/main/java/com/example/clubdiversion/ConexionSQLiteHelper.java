package com.example.clubdiversion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.clubdiversion.Utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREA_TABLA_DOC);
        db.execSQL(Utilidades.CREA_TABLA_DUDA);
        db.execSQL(Utilidades.CREA_TABLA_INSTA);
        db.execSQL(Utilidades.CREAR_TABLA_USERS); // Nueva tabla de usuarios

    }//TABLA_SOCIO

    @Override
    public void onUpgrade(SQLiteDatabase db, int VersionAntigua, int VersionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.T_Doc);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.T_Duda);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.T_INST);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_USERS); // Nueva tabla de usuarios
        onCreate(db);

    }
}

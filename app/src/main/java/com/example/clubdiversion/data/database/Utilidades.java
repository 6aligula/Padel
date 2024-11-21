package com.example.clubdiversion.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Utilidades {

    public static Long Insertar_En_Tabla(String tabla, ContentValues values, SQLiteDatabase db )
    {
        Long idResul=-1L;
        idResul = db.insert(tabla,null,values);
        return idResul;
    }

    public static Cursor Listar_Tabla(SQLiteDatabase db , String query)
    {
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    public static boolean BuscaLogica(SQLiteDatabase db , String query)
    {
        Cursor c  = db.rawQuery(query,null);
        if (c.moveToFirst())
        {
            Log.e("Salida","Si existe");
            return true;
        }
        else
            Log.e("Salida","No existe");
        return false;
    }

    public static Cursor BuscaSocio(SQLiteDatabase db , String query)
    {
        Cursor c  = db.rawQuery(query,null);

        return c;
    }

    public static int Upgrade_Tabla(String tabla, ContentValues values, String where, String[] args, SQLiteDatabase db )
    {
        int idResul=-1;
        idResul = db.update(tabla, values, where, args);
        return idResul;
    }

    public static int Eliminar_Tabla(String tabla, String where, String[] args, SQLiteDatabase db )
    {
        int i=0;
        db.delete(tabla, where, args);
        return i;
    }

}


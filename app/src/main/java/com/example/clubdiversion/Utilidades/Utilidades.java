package com.example.clubdiversion.Utilidades;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Utilidades {

    public static final String TABLA_USERS = "users";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_USERNAME = "username";
    public static final String CAMPO_NAME = "name";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_IS_ADMIN = "isAdmin";
    public static final String CAMPO_TOKEN = "token";

    public static final String CREAR_TABLA_USERS =
            "CREATE TABLE " + TABLA_USERS + " (" +
                    CAMPO_ID + " INTEGER PRIMARY KEY, " +
                    CAMPO_USERNAME + " TEXT, " +
                    CAMPO_NAME + " TEXT, " +
                    CAMPO_DIRECCION + " TEXT, " +
                    CAMPO_TELEFONO + " TEXT, " +
                    CAMPO_IS_ADMIN + " INTEGER, " + // 0 = false, 1 = true
                    CAMPO_TOKEN + " TEXT)";


    //CAMPOS documento
    public static final String T_Doc = "documentos";
    public static final String DOC_ID = "id";
    public static final String DOC_DES = "descripcion";
    public static final String DOC_LINK = "link";

    //CREA TABLA documento
    public static final String CREA_TABLA_DOC = "CREATE TABLE "+T_Doc +
            "("+DOC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+DOC_DES+" TEXT," +
            ""+DOC_LINK+" TEXT)";

    //CAMPOS dudas
    public static final String T_Duda = "duda";
    public static final String DUDA_ID = "id";
    public static final String DUDA_DUDA = "txtduda";
    public static final String DUDA_RESP = "txtresp";

    //CREA TABLA documento
    public static final String CREA_TABLA_DUDA = "CREATE TABLE "+T_Duda +
            " ("+DUDA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+DUDA_DUDA+" TEXT, " +
            ""+DUDA_RESP+" TEXT)";


    //CAMPOS instalacion
    public static final String T_INST = "insta";
    public static final String INST_ID = "id";
    public static final String INST_ID_INST = "idInsta";
    public static final String INST_FECHA = "Fecha";

    //CREA TABLA Instalaciones
    public static final String CREA_TABLA_INSTA = "CREATE TABLE "+T_INST +
            " ("+INST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+INST_ID_INST+" TEXT, " +
            ""+INST_FECHA+" TEXT)";



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

    public static boolean isCurrentUserAdmin(SQLiteDatabase db) {
        boolean isAdmin = false;
        Cursor cursor = null;

        try {
            String query = "SELECT " + CAMPO_IS_ADMIN + " FROM " + TABLA_USERS + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                int isAdminValue = cursor.getInt(0); // CAMPO_IS_ADMIN
                isAdmin = isAdminValue == 1;
            }
        } catch (Exception e) {
            Log.e("Utilidades", "Error al verificar administrador: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        return isAdmin;
    }

}

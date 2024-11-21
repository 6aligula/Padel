package com.example.clubdiversion.data.database;

// Archivo para constantes relacionadas con las tablas de la base de datos
public class DatabaseSchema {
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
                    CAMPO_IS_ADMIN + " INTEGER, " +
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


    // CAMPOS instalacion
    public static final String T_INST = "insta";
    public static final String INST_ID = "id";
    public static final String INST_ID_INST = "idInsta";
    public static final String INST_FECHA = "Fecha";
    public static final String INST_SYNCED = "inst_synced"; // Nuevo campo para sincronización

    // CREA TABLA Instalaciones
    public static final String CREA_TABLA_INSTA = "CREATE TABLE " + T_INST +
            " (" + INST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + INST_ID_INST + " TEXT, " +
            "" + INST_FECHA + " TEXT, " +
            "" + INST_SYNCED + " INTEGER DEFAULT 0)"; // Se añade INST_SYNCED

}


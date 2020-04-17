package com.example.nihilist.BBDD;

public class EstructuraDB {
    // TABLA MENSAJES
    public static final String TABLE_NAME_MENSAJES="table_message";
    public static final String NOMBRE_CAMPO1="msgIdb";
    public static final String NOMBRE_CAMPO2="byId";
    public static final String NOMBRE_CAMPO3="toId";
    public static final String NOMBRE_CAMPO4="mesage";
    public static final String NOMBRE_CAMPO5="fecha";

    // CREATES
    public static final String SQL_CREATE_MENSAJES =
            " CREATE TABLE " + EstructuraDB.TABLE_NAME_MENSAJES + " (" +
                    EstructuraDB.NOMBRE_CAMPO1 + " TEXT PRIMARY KEY AUTOINCREMENT,"+
                    EstructuraDB.NOMBRE_CAMPO2 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO3 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO4 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO5 + " TEXT)";

    // DELETES
    public static final  String SQL_DELETE_MENSAJES =
            "DROP TABLE IF EXISTS "+EstructuraDB.TABLE_NAME_MENSAJES;

}

package com.example.nihilist.BBDD;

public class EstructuraDB {
    // TABLA MENSAJES
    public static final String TABLE_NAME_MENSAJES="table_mesages";
    public static final String NOMBRE_CAMPO1="msgIdb";
    public static final String NOMBRE_CAMPO2="byId";
    public static final String NOMBRE_CAMPO3="toId";
    public static final String NOMBRE_CAMPO4="mesage";
    public static final String NOMBRE_CAMPO5="fecha";

    // TABLA USUARIOS
    public static final String TABLE_NAME_USUARIOS="table_mesages";
    public static final String NOMBRE_CAMPO6="idUsuario"; //DNI
    public static final String NOMBRE_CAMPO7="tipo";
    public static final String NOMBRE_CAMPO8="name";
    public static final String NOMBRE_CAMPO9="surname";
    public static final String NOMBRE_CAMPO10="email";

    // CREATES
    public static final String SQL_CREATE_MENSAJES =
            " CREATE TABLE " + EstructuraDB.TABLE_NAME_MENSAJES + " (" +
                    EstructuraDB.NOMBRE_CAMPO1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    EstructuraDB.NOMBRE_CAMPO2 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO3 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO4 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO5 + " TEXT)";

    public static final String SQL_CREATE_USUARIOS =
            " CREATE TABLE " + EstructuraDB.TABLE_NAME_USUARIOS + " (" +
                    EstructuraDB.NOMBRE_CAMPO6 + " TEXT PRIMARY KEY,"+
                    EstructuraDB.NOMBRE_CAMPO7 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO8 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO9 + " TEXT,"+
                    EstructuraDB.NOMBRE_CAMPO10 + " TEXT)";

    // DELETES
    public static final  String SQL_DELETE_MENSAJES =
            "DROP TABLE IF EXISTS "+EstructuraDB.TABLE_NAME_MENSAJES;

    public static final  String SQL_DELETE_USUARIOS =
            "DROP TABLE IF EXISTS "+EstructuraDB.TABLE_NAME_USUARIOS;
}

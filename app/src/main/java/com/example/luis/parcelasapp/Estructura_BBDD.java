package com.example.luis.parcelasapp;

/**
 * Created by Luis on 23/08/2017.
 */

public class Estructura_BBDD {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    public Estructura_BBDD() {}

    /* Inner class that defines the table contents */
    //public static class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "datosParcelas";
    public static final String ID = "_id";
    public static final String NOMBRE_COLUMNA1 = "parcela";
    public static final String NOMBRE_COLUMNA2 = "localidad";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    //}
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.LATITUD + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.LONGITUD + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;

    public int id;
    public String parcela;
    public String localidad;
    public String latitud;
    public String longitud;
}

package com.example.luis.parcelasapp;

/**
 * Created by Luis on 12/09/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class ParcelasRepo {
    private BBDD_Helper dbHelper;

    public ParcelasRepo(Context context) {
        dbHelper = new BBDD_Helper(context);
    }

    public int insert(Estructura_BBDD student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD.NOMBRE_COLUMNA1, student.parcela);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA2,student.localidad);

        // Inserting Row
        long student_Id = db.insert(Estructura_BBDD.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public Cursor  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Estructura_BBDD.ID + "," +
                Estructura_BBDD.NOMBRE_COLUMNA1 + "," +
                Estructura_BBDD.NOMBRE_COLUMNA2 +
                " FROM " + Estructura_BBDD.TABLE_NAME;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    public Cursor  getStudentListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Estructura_BBDD.ID + "," +
                Estructura_BBDD.NOMBRE_COLUMNA1 + "," +
                Estructura_BBDD.NOMBRE_COLUMNA2 +
                " FROM " + Estructura_BBDD.TABLE_NAME +
                " WHERE " +  Estructura_BBDD.NOMBRE_COLUMNA1 + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    public Estructura_BBDD getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                Estructura_BBDD.ID + "," +
                Estructura_BBDD.NOMBRE_COLUMNA1 + "," +
                Estructura_BBDD.NOMBRE_COLUMNA2 +
                " FROM " + Estructura_BBDD.TABLE_NAME
                + " WHERE " +
                Estructura_BBDD.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Estructura_BBDD student = new Estructura_BBDD();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.id =cursor.getInt(cursor.getColumnIndex(Estructura_BBDD.ID));
                student.parcela =cursor.getString(cursor.getColumnIndex(Estructura_BBDD.NOMBRE_COLUMNA1));
                student.localidad  =cursor.getString(cursor.getColumnIndex(Estructura_BBDD.NOMBRE_COLUMNA2));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }




}
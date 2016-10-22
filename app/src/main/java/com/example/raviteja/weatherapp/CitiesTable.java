package com.example.raviteja.weatherapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class CitiesTable {
    static final String TABLENAME = "cities";
    static final String COLOUMN_ID= "_id";
    static final String COLOUMN_CITYNAME = "cityName";
    static final String COLOUMN_COUNTRY = "country";
    static final String COLOUMN_TEMPERATURE = "temperature";
    static final String COLOUMN_FAVORITE = "favorite";

    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME + "(");
        sb.append(COLOUMN_ID +" integer primary key autoincrement,");
        sb.append(COLOUMN_CITYNAME +" text not null, ");
        sb.append(COLOUMN_COUNTRY +" text not null, ");
        sb.append(COLOUMN_TEMPERATURE +" text not null, ");
        sb.append(COLOUMN_FAVORITE +" text not null); ");

        try{
            db.execSQL(sb.toString());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    static  public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        CitiesTable.onCreate(db);
    }



}

package com.example.raviteja.weatherapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class CitiesDAO {
    private SQLiteDatabase db;

    public CitiesDAO(SQLiteDatabase db){
        this.db=db;
    }

    public long save(Cities cities){
        ContentValues values = new ContentValues();
        values.put(CitiesTable.COLOUMN_CITYNAME,cities.getCityName());
        values.put(CitiesTable.COLOUMN_COUNTRY,cities.getCountry());
        values.put(CitiesTable.COLOUMN_FAVORITE,cities.getFavorite());
        values.put(CitiesTable.COLOUMN_TEMPERATURE,cities.getTemperature());
        return db.insert(CitiesTable.TABLENAME,null,values);
    }

    public boolean update(Cities cities){
        ContentValues values = new ContentValues();
        values.put(CitiesTable.COLOUMN_CITYNAME,cities.getCityName());
        values.put(CitiesTable.COLOUMN_COUNTRY,cities.getCountry());
        values.put(CitiesTable.COLOUMN_FAVORITE,cities.getFavorite());
        values.put(CitiesTable.COLOUMN_TEMPERATURE,cities.getTemperature());
        return db.update(CitiesTable.TABLENAME,values,CitiesTable.COLOUMN_ID+"=?",new String[]{cities.get_id()+""})>0;
    }

    public boolean delete(Cities cities){
        return db.delete(CitiesTable.TABLENAME,CitiesTable.COLOUMN_ID +"=?", new String[]{cities.get_id()+""})>0;
    }

    public Cities get(String cityName){
        Cities cities = null;

        Cursor c=db.query(true, CitiesTable.TABLENAME,new String[]{CitiesTable.COLOUMN_ID,CitiesTable.COLOUMN_CITYNAME,CitiesTable.COLOUMN_COUNTRY,CitiesTable.COLOUMN_FAVORITE,CitiesTable.COLOUMN_TEMPERATURE,}
                ,CitiesTable.COLOUMN_CITYNAME + "=?", new String[]{cityName+ ""},null,null,null,null,null);

        if(c!=null&&c.moveToFirst()){
            cities=buildCityFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return cities;
    }

    public List<Cities> getAll(){
        List<Cities> citiesList = new ArrayList<Cities>();
        Cursor c=db.query(CitiesTable.TABLENAME,new String[]{
                        CitiesTable.COLOUMN_ID,CitiesTable.COLOUMN_CITYNAME,CitiesTable.COLOUMN_COUNTRY,CitiesTable.COLOUMN_FAVORITE,CitiesTable.COLOUMN_TEMPERATURE}
                ,null,null,null,null,null);
        if(c!=null&&c.moveToFirst())
        {
            do{
                Cities cities = buildCityFromCursor(c);
                if(cities!=null)
                {
                    citiesList.add(cities);
                }
            }while(c.moveToNext());
            if(!c.isClosed()){
                c.close();
            }
        }
        return citiesList;
    }

    private Cities buildCityFromCursor(Cursor c){
        Cities cities = null;
        if(c!=null){
            cities = new Cities();
            cities.set_id(c.getLong(0));
            cities.setCityName(c.getString(1));
            cities.setCountry(c.getString(2));
            cities.setFavorite(c.getString(3));
            cities.setTemperature(c.getString(4));
        }
        return cities;
    }

}

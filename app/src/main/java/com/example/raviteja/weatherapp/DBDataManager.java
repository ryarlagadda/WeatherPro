package com.example.raviteja.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class DBDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenhelper;
    private SQLiteDatabase db;
    private CitiesDAO citiesDAO;

    public DBDataManager(Context mContext){
        this.mContext = mContext;
        dbOpenhelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenhelper.getWritableDatabase();
        citiesDAO = new CitiesDAO(db);
    }

    public void close(){
        if(db !=null)
        {
            db.close();
        }
    }

    public CitiesDAO getCitiesDAO(){
        return this.citiesDAO;
    }

    public long saveCities(Cities cities){
        return this.citiesDAO.save(cities);
    }

    public boolean updateCities(Cities cities){
        return this.citiesDAO.update(cities);
    }

    public boolean deleteCities(Cities cities){
        return this.citiesDAO.delete(cities);
    }

    public Cities getCities(String cityName){
        return this.citiesDAO.get(cityName);
    }

    public List<Cities> getAllCities(){
        return this.citiesDAO.getAll();
    }

}

package com.example.raviteja.weatherapp;

/**
 * Created by RAVITEJA on 10/4/2016.
 */


/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* */
public class Favorites {

    String city,state,storing_date,temperature_at_storage_time;

    @Override
    public String toString() {
        return "Favorites{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", storing_date='" + storing_date + '\'' +
                ", temperature_at_storage_time='" + temperature_at_storage_time + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStoring_date() {
        return storing_date;
    }

    public void setStoring_date(String storing_date) {
        this.storing_date = storing_date;
    }

    public String getTemperature_at_storage_time() {
        return temperature_at_storage_time;
    }

    public void setTemperature_at_storage_time(String temperature_at_storage_time) {
        this.temperature_at_storage_time = temperature_at_storage_time;
    }
}

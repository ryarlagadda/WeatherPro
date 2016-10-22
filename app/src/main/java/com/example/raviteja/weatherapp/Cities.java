package com.example.raviteja.weatherapp;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class Cities {
    long _id;
    String cityName,country,temperature,favorite;

    public Cities(String cityName, String country, String temperature, String favorite) {
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.favorite = favorite;

    }

    public Cities() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "_id=" + _id +
                ", cityName='" + cityName + '\'' +
                ", country='" + country + '\'' +
                ", temperature='" + temperature + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}

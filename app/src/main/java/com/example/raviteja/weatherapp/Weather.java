package com.example.raviteja.weatherapp;

import java.io.Serializable;

/**
 * Created by RAVITEJA on 10/5/2016.
 */

/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* */
public class Weather implements Serializable{
    String CityName;
    String StateName;
    String time;
    Double temperature;
    String dewpoint;
    String clouds;
    String iconUrl;
    String windSpeed;
    String windDirection;
    String climateType;
    String humidity;
    String feelsLike;
    Double maximumTemp;
    Double minimumTemp;
    String pressure;



    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(Double maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public Double getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(Double minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "CityName='" + CityName + '\'' +
                ", StateName='" + StateName + '\'' +
                ", time='" + time + '\'' +
                ", temperature=" + temperature +
                ", dewpoint='" + dewpoint + '\'' +
                ", clouds='" + clouds + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", maximumTemp=" + maximumTemp +
                ", minimumTemp=" + minimumTemp +
                ", pressure='" + pressure + '\'' +
                '}';
    }
}

package com.example.raviteja.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RAVITEJA on 10/5/2016.
 */

public class WeatherUtil {

    static public class PullWeather{
        static ArrayList<Weather> parseWeather(String in,String cityName,String stateName) throws JSONException {

            ArrayList<Weather> weatherList = new ArrayList<>();
            JSONObject root = new JSONObject(in);

            if(root.has("error"))
            {
                return null;
            }
            else {
                JSONArray weatherJSONArray = root.getJSONArray("list");
                Log.d("outside", "checking");
                for (int i = 0; i < weatherJSONArray.length(); i++) {
                    JSONObject weatherJSONobject = weatherJSONArray.getJSONObject(i);
                    Weather weather = new Weather();
                    Log.d("inside", "checking");
                    weather.setPressure(weatherJSONobject.getJSONObject("main").getString("pressure"));
                    weather.setTemperature(Double.parseDouble(weatherJSONobject.getJSONObject("main").getString("temp"))-273.15);
                    weather.setTime(weatherJSONobject.getString("dt_txt"));
                    JSONArray weatherArray = weatherJSONobject.getJSONArray("weather");
                    JSONObject weathertempJSONobject = weatherArray.getJSONObject(0);
                    weather.setClouds(weathertempJSONobject.getString("description"));
                    weather.setIconUrl(weathertempJSONobject.getString("icon"));
                    weather.setWindSpeed(weatherJSONobject.getJSONObject("wind").getString("speed"));
                    weather.setWindDirection(weatherJSONobject.getJSONObject("wind").getString("deg"));
                    weather.setHumidity(weatherJSONobject.getJSONObject("main").getString("humidity"));
                    weather.setMaximumTemp(Double.parseDouble(weatherJSONobject.getJSONObject("main").getString("temp_max"))-273.15);
                    weather.setMinimumTemp(Double.parseDouble(weatherJSONobject.getJSONObject("main").getString("temp_min"))-273.15);
                    weather.setCityName(cityName);
                    weather.setStateName(stateName);
                    Log.d("weather",weather.toString());
                    weatherList.add(weather);
                }

                return weatherList;
            }



        }
    }
}

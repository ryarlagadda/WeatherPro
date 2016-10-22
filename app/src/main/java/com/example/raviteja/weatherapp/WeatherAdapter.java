package com.example.raviteja.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RAVITEJA on 10/6/2016.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {

    List<Weather> weatherList;
    Context mContext;
    int mResource;
    ImageView imageView;

    public WeatherAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.weatherList=objects;
        Log.d("inside","inside adap");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        Weather weather = weatherList.get(position);
        imageView = (ImageView) convertView.findViewById(R.id.weatherImage);
        TextView textView_climateType = (TextView) convertView.findViewById(R.id.climateType_tv);
        TextView textView_time = (TextView) convertView.findViewById(R.id.weatherTime_tv);
        String time= weather.getTime();
        String[] timesplit=time.split(" ");
        textView_climateType.setText(weather.getClimateType());
        textView_time.setText(timesplit[1]);
        ((TextView)convertView.findViewById(R.id.temperature_val_tv)).setText(weather.getTemperature()+"°F");
        Picasso.with(mContext).load("http://openweathermap.org/img/w/"+weather.getIconUrl()+".png").into(imageView);
        return convertView;
    }


}

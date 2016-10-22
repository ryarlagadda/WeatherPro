package com.example.raviteja.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder> {

    CityWeatherActivity mContext;
    RecyclerView mRecyclerView;
    List<Weather> mWeathers = new ArrayList<Weather>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView weatherTimeDetail;
        public TextView temperatureDetail;
        public TextView conditionDetail;
        public TextView humidityDetail;
        public TextView pressureDetail;
        public TextView windDetail;
        public ImageView weather_imageDetail;

        public ViewHolder(View itemView) {

            super(itemView);
            Log.d("in detail adapter","check");
            weatherTimeDetail = (TextView) itemView.findViewById(R.id.weatherTime_bottomRV);
            conditionDetail = (TextView) itemView.findViewById(R.id.condition_bottomRV);
            humidityDetail = (TextView) itemView.findViewById(R.id.humidity_bottomRV);
            temperatureDetail = (TextView) itemView.findViewById(R.id.temperature_bottomRV);
            pressureDetail = (TextView) itemView.findViewById(R.id.pressure_bottomRV);
            windDetail = (TextView) itemView.findViewById(R.id.wind_bottomRV);
            weather_imageDetail = (ImageView) itemView.findViewById(R.id.weather_image_bottomRV);

        }
    }

    public DetailRecyclerAdapter(CityWeatherActivity context, List<Weather> weathers, RecyclerView recyclerView) {
        mWeathers = weathers;
        mContext = context;
        mRecyclerView =recyclerView;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View weatherView = inflater.inflate(R.layout.bottom_recycler_activity_row_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(weatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Weather weather = mWeathers.get(position);

        TextView time = holder.weatherTimeDetail;
         TextView condition = holder.conditionDetail;
        TextView pressure = holder.pressureDetail;
        TextView temperature = holder.temperatureDetail;
        TextView humidity = holder.humidityDetail;
        TextView wind = holder.windDetail;
        ImageView image1 = holder.weather_imageDetail;
        String[] date=weather.getTime().split(" ");
        String[] finaldate = date[1].split(":");
        if(Integer.parseInt(finaldate[0])>=12)
        {
            int hours=Integer.parseInt(finaldate[0])-12;
            time.setText(Integer.toString(hours)+":"+finaldate[1]+" PM");
        }
        else if(Integer.parseInt(finaldate[0])<12)
        {
            time.setText(finaldate[0]+":"+finaldate[1]+" AM");
        }
        DecimalFormat df = new DecimalFormat("###.##");
        if(this.mContext.temperatureUnit.equals("F")){
            temperature.setText("Temperature: "+new DecimalFormat("##.##").format(((weather.getTemperature())*9/5)+32)+"° F");
        }
        else{
            temperature.setText("Temperature: "+new DecimalFormat("##.##").format((weather.getTemperature()))+"° C");
        }
        pressure.setText("Pressure: "+weather.getPressure()+"hPa");
        humidity.setText("Humidity: "+weather.getHumidity()+"%");
        wind.setText("Wind: "+weather.getWindSpeed()+" mps"+", "+weather.getWindDirection()+"° ESE");
        condition.setText("Condition: "+weather.getClouds());
        Picasso.with(mContext).load("http://openweathermap.org/img/w/"+weather.getIconUrl()+".png").into(image1);
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

}

package com.example.raviteja.weatherapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by RAVITEJA on 10/20/2016.
 */
public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>{

    Weather previousWeather=null;
List<Weather> displayedWeathers = new ArrayList<Weather>();
    List<Weather> detailWeathers = new ArrayList<Weather>();

    static CityWeatherActivity mContext;
   static RecyclerView mRecyclerView;
    static List<Weather> mWeathers = new ArrayList<Weather>();
    static ArrayList<Weather> allWeather;
    int count=0;
    public WeatherRecyclerAdapter(CityWeatherActivity context, List<Weather> weathers, RecyclerView recyclerView,ArrayList<Weather> weathersList) {
        mWeathers = weathers;
        mContext = context;
        mRecyclerView = recyclerView;
        allWeather = weathersList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView weather_date1;
        public TextView weather_temp1;
        public ImageView weather_image1;
        public ViewHolder(View itemView) {
            super(itemView);
            weather_date1 = (TextView) itemView.findViewById(R.id.weather_date1_rv);
            weather_temp1 = (TextView) itemView.findViewById(R.id.weather_temp1_rv);
            weather_image1 = (ImageView) itemView.findViewById(R.id.weather_image1_rv);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View weatherView = inflater.inflate(R.layout.recycler_weather_activity_row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(weatherView);
        weatherView.setOnClickListener(new MyListner());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

                Weather weather = mWeathers.get(position);
                TextView date1 = holder.weather_date1;
                TextView temp1 = holder.weather_temp1;
                ImageView image1 = holder.weather_image1;
                String[] date = weather.getTime().split(" ");
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                Date date12 = null;
                try {
                    date12 = inputFormat.parse(date[0]);
                    date1.setText(outputFormat.format(date12));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        if(this.mContext.temperatureUnit.equals("F")){
            temp1.setText(new DecimalFormat("##.##").format(((weather.getTemperature())*9/5)+32)+"°F");
        }
        else{
            temp1.setText(new DecimalFormat("##.##").format((weather.getTemperature()))+"°C");
        }
                Picasso.with(mContext).load("http://openweathermap.org/img/w/" + weather.getIconUrl() + ".png").into(image1);
                displayedWeathers.add(weather);
                count = 0;
                Log.d("weather added to disp", weather.toString());
    }
    class MyListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.indexOfChild(v);
            Log.d("Clicked and Position",String.valueOf(itemPosition));
            int position = mRecyclerView.getChildLayoutPosition(v);
            Log.d("position Clicked",Integer.toString(position));
            String date1=mWeathers.get(position).getTime().split(" ")[0];
           List<Weather> detailWeather = new ArrayList<Weather>();
            for(int i=0;i<allWeather.size();i++)
            {
                String date2=allWeather.get(i).getTime().split(" ")[0];
                if(date1.equals(date2))
                {
                    detailWeather.add(allWeather.get(i));
                }
            }
            TextView threehourly= (TextView) mContext.findViewById(R.id.threeHourlyForecast);
            String[] date=mWeathers.get(position).getTime().split(" ");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);
            Date date12 = null;
            try {
                date12 = inputFormat.parse(date[0]);
                threehourly.setText("Three Hourly Forecast on "+outputFormat.format(date12));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            LinearLayoutManager DetailHorizontalLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
            RecyclerView rvWeatherDetail = (RecyclerView) mContext.findViewById(R.id.DetailHorizontal_recycler_view);
            rvWeatherDetail.setLayoutManager(DetailHorizontalLayoutManager);
            DetailRecyclerAdapter adapter1 = new DetailRecyclerAdapter(mContext,detailWeather,rvWeatherDetail);
            rvWeatherDetail.setAdapter(adapter1);
        }
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }
}

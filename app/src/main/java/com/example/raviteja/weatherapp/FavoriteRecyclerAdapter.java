package com.example.raviteja.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by RAVITEJA on 10/19/2016.
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    DBDataManager dm;
    int i=0;
    Cities city=null;
    private static ClickListener clickListener;
    MainActivity mContext;
    RecyclerView mRecyclerView;
    List<Cities> mCities;

    public FavoriteRecyclerAdapter( MainActivity activity,List<Cities> favorites, RecyclerView mRecyclerView) {
        this.mCities = favorites;
        this.mContext = activity;
        this.mRecyclerView=mRecyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        public TextView CityName;
        public TextView Temperature;
        public TextView UpdateDate;
        public ImageView FavoriteImage;

        public ViewHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            CityName = (TextView) itemView.findViewById(R.id.Cityname_rv);
            Temperature = (TextView) itemView.findViewById(R.id.temperature_rv);
            UpdateDate = (TextView) itemView.findViewById(R.id.updateddate_rv);
            FavoriteImage = (ImageView) itemView.findViewById(R.id.favoriteImage_rv);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    private Context getContext() {
        return mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View weatherView = inflater.inflate(R.layout.recycler_row_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(weatherView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        city = mCities.get(position);
        dm = new DBDataManager(mContext);

        TextView cityName = holder.CityName;
        TextView temperature = holder.Temperature;
        TextView updateDate = holder.UpdateDate;
        final ImageView starImage = holder.FavoriteImage;
        Log.d("temp",city.getTemperature());
        cityName.setText(city.getCityName()+","+city.getCountry());
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor=preferences.edit();
        String date1=preferences.getString(city.getCityName(),"");
        String[] date = date1.split(" ");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date12 = null;
        try {
            date12 = inputFormat.parse(date[0]);
            updateDate.setText("Updated on:"+outputFormat.format(date12));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("###.##");
        if(this.mContext.temperatureUnit.equals("F")){
            temperature.setText(new DecimalFormat("##.##").format((Double.parseDouble(city.getTemperature())*9/5)+32)+"°F");
        }
        else{
            temperature.setText(new DecimalFormat("##.##").format(Double.parseDouble(city.getTemperature()))+"°C");
        }

        if(city.getFavorite().equals("true"))
        {
            starImage.setImageResource(R.drawable.star_gold);
            starImage.setTag("gold-"+position);
        }
        else if(city.getFavorite().equals("false")) {
            starImage.setImageResource(R.drawable.star_gray);
            starImage.setTag("gray-" + position);
        }

        starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] tag=starImage.getTag().toString().split("-");
                if(tag[0].equals("gray")) {
                    starImage.setImageResource(R.drawable.star_gold);
                    starImage.setTag("gold-"+tag[1]);
                    Cities fav = mCities.get(Integer.parseInt(tag[1]));
                    fav.setFavorite("true");
                    dm.updateCities(fav);
                    Log.d("stargold",fav.toString());
                }
                else{
                    starImage.setImageResource(R.drawable.star_gray);
                    starImage.setTag("gray-"+tag[1]);
                    Cities fav = mCities.get(Integer.parseInt(tag[1]));
                    fav.setFavorite("false");
                    dm.updateCities(fav);
                    Log.d("stargray",fav.toString());
                }

                mContext.updateFavs(mCities);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CityWeatherActivity.class);
                intent.putExtra("CityName", mCities.get(position).getCityName());
                intent.putExtra("StateName", mCities.get(position).getCountry());
                Log.d("moved from adapter","done");
                getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dm.deleteCities(mCities.get(position));
                Toast.makeText(getContext(), "City Deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}

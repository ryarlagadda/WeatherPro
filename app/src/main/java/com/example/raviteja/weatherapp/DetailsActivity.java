package com.example.raviteja.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.solidfire.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    Weather weather=null;
    int m=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("City Weather");
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.view3);
        setSupportActionBar(mtoolbar);
        TextView currentlocation = (TextView) findViewById(R.id.current_location_val_tv);
        TextView temperature = (TextView) findViewById(R.id.temperature_val_tv);
        TextView weatherType = (TextView) findViewById(R.id.weathertype_val_tv);
        TextView maxTemp = (TextView) findViewById(R.id.max_temperature_val_tv);
        TextView minTemp = (TextView) findViewById(R.id.Min_temp_val_tv);
        TextView feelslike = (TextView) findViewById(R.id.feelsLike_val_tv);
        TextView humidity = (TextView) findViewById(R.id.humidity_val_tv);
        TextView dewpoint = (TextView) findViewById(R.id.dewpoint_val_tv);
        TextView pressure = (TextView) findViewById(R.id.pressure_val_tv);
        TextView clouds = (TextView) findViewById(R.id.clouds_val_tv);
        TextView winds = (TextView) findViewById(R.id.winds_val_tv);
        ImageView image = (ImageView) findViewById(R.id.weather_image);

        if (getIntent().getExtras() !=null) {
            Bundle b = getIntent().getExtras();
            weather = (Weather) b.get("KEY");
            currentlocation.setText(getIntent().getExtras().get("loc").toString());
            Picasso.with(DetailsActivity.this).load(weather.getIconUrl()).resize(75,50).into(image);
            temperature.setText(weather.getTemperature()+"°F");
            weatherType.setText(weather.getClimateType());
            maxTemp.setText("Max Temperature: ");
            minTemp.setText("Min Temperature: ");
            feelslike.setText(weather.getFeelsLike()+" Fahrenheit");
            humidity.setText(weather.getHumidity()+"%");
            dewpoint.setText(weather.getDewpoint()+" Fahrenheit");
            pressure.setText(weather.getPressure()+" hPa");
            maxTemp.setText("Max Temperature:"+weather.getMaximumTemp()+" Farenheit");
            minTemp.setText("Min Temperature: "+ weather.getMinimumTemp()+" Farenheit");
            clouds.setText(weather.getClouds());
            winds.setText(weather.getWindSpeed()+" mph"+","+weather.getWindDirection()+"");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.custom_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_setting)
        {
            Boolean flag=true;
            SharedPreferences mPrefs=getSharedPreferences("Favorites", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(weather); // myObject - instance of MyObject
            while(flag)
            {
                if(mPrefs.contains("MyObject"+m))
                {
                    String json2 = mPrefs.getString("MyObject"+m, "");
                    Weather obj = gson.fromJson(json2, Weather.class);
                    if((obj.getCityName()).equals(weather.getCityName())&&(obj.getStateName()).equals(weather.getStateName()))
                    {
                        Log.d("matched","MyObject"+m);
                        prefsEditor.remove("MyObject"+m);
                        prefsEditor.putString("MyObject"+m,json);
                        prefsEditor.commit();
                        flag=false;
                        Toast.makeText(DetailsActivity.this, "Updated to Favorites", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        m++;
                    }
                }
                else{
                    prefsEditor.putString("MyObject"+m,json);
                    prefsEditor.commit();
                    flag=false;
                    Toast.makeText(DetailsActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

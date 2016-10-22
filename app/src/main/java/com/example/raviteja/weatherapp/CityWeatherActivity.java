package com.example.raviteja.weatherapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    WeatherAdapter adapter;
    ListView listView;
    int m=0;
    String CityName,StateName,Temperature,curr_Date;
    ArrayList<Weather> weathersList;
    DBDataManager dm;
    List<Weather> temp;
    List<Weather> temp2;
    RecyclerView rvWeatherDetail;
    String temperatureUnit="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("City Weather");
        setContentView(R.layout.activity_city_weather);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.view2);
        rvWeatherDetail = (RecyclerView) findViewById(R.id.DetailHorizontal_recycler_view);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"C");
        temperatureUnit=preferenceTempUnit;

        setSupportActionBar(mtoolbar);
        if (getIntent().getExtras() !=null) {
            Bundle b = getIntent().getExtras();
            CityName=b.get("CityName").toString();
            StateName = b.get("StateName").toString();
            CityName=CityName.replace(" ","_");
            Log.d("city",CityName);
            new GetData().execute("http://api.openweathermap.org/data/2.5/forecast?q="+CityName+","+StateName+"&mode=JSON&appid=a3498043560466993c0ab7629296d744");
        }
    }

    @Override
    protected void onResume() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"C");
        temperatureUnit=preferenceTempUnit;
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        if(dm!=null){
        dm.close();}
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.city_weather_custom_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_city)
        {
            dm = new DBDataManager(this);
            if(dm.getCities(weathersList.get(0).getCityName())!=null) {
                dm.updateCities(new Cities(weathersList.get(0).getCityName(), weathersList.get(0).getStateName(), weathersList.get(0).getTemperature().toString(), "false"));
                Toast.makeText(CityWeatherActivity.this, "Updated to Favorites", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=preferences.edit();
                editor.remove(weathersList.get(0).getCityName());
                editor.commit();
                editor.putString(weathersList.get(0).getCityName(),weathersList.get(0).getTime());
                editor.commit();
            }
            else
            {
                dm.saveCities(new Cities(weathersList.get(0).getCityName(), weathersList.get(0).getStateName(), weathersList.get(0).getTemperature().toString(), "false"));
                Toast.makeText(CityWeatherActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(weathersList.get(0).getCityName(),weathersList.get(0).getTime());
                editor.commit();
            }
        }
        else if(item.getItemId()==R.id.settings)
        {
            Intent intPrefs=new Intent(CityWeatherActivity.this,PrefsActivity.class);
            startActivity(intPrefs);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class GetData extends AsyncTask<String,Void,ArrayList<Weather>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CityWeatherActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading Hourly Data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Weather> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Log.d("inside doback","check");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                Log.d("inside doback","coneected");
                int statuscode = con.getResponseCode();
                if(statuscode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while(line!=null)
                    {
                        sb.append(line);
                        Log.d("String",line);
                        line = reader.readLine();
                    }

                    return WeatherUtil.PullWeather.parseWeather(sb.toString(),CityName, StateName);
                }
            } catch (MalformedURLException e) {
                Log.d("url","wrong");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<Weather> weathers) {
            if (weathers == null) {
                Intent intent = new Intent(CityWeatherActivity.this,MainActivity.class);
                intent.putExtra("Error","Error");
                startActivity(intent);
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Log.d("check", weathers.get(0).getClouds());
                weathersList=weathers;
                Temperature= weathers.get(0).getTemperature()+"°C";
                String[] time=weathers.get(0).getTime().split("-");
                curr_Date=time[0];
                boolean flag=true;
                temp = new ArrayList<Weather>();
                temp.add(weathers.get(0));
                for(int i=0;i<weathers.size();i++)
                {
                    for(int j=0;j<temp.size();j++)
                    {
                        String[] previousDate = temp.get(j).getTime().split(" ");
                        String[] currentDate = weathers.get(i).getTime().split(" ");
                        if(previousDate[0].equals(currentDate[0]))
                        {
                            flag=false;
                        }
                    }
                    if(flag==true)
                    {
                        temp.add(weathers.get(i));
                    }
                    flag=true;
                }
                int count=0;
                Weather tempWeather= new Weather();
                for(int i=0;i<weathers.size();i++)
                {

                    for(int j=0;j<temp.size();j++)
                    {
                        String[] previousDate = temp.get(j).getTime().split(" ");
                        String[] currentDate = weathers.get(i).getTime().split(" ");
                        if(previousDate[0].equals(currentDate[0]))
                        {
                            flag=false;
                        }
                    }
                    if(flag==true)
                    {
                        temp.add(weathers.get(i));
                    }
                   flag=true;
                }

                for(int i=0;i<temp.size();i++)
                {
                    int z=0;
                    int[] index = new int[20];
                    Double avgTemp=0.0;
                    for(int j=0;j<weathers.size();j++)
                    {
                        String[] previousDate = temp.get(i).getTime().split(" ");
                        String[] currentDate = weathers.get(j).getTime().split(" ");
                        if(previousDate[0].equals(currentDate[0]))
                        {
                            avgTemp=avgTemp+weathers.get(j).getTemperature();
                            index[z]=j;
                            z++;
                            count++;
                            flag=false;
                        }
                    }

                    if(count>0)
                    {
                        avgTemp=avgTemp/count;
                        tempWeather= temp.get(i);
                        tempWeather.setTemperature(avgTemp);
                        if((z+1)%2==0)
                        {
                            Log.d("Icon even",Integer.toString((z+1)));
                            tempWeather.setIconUrl(weathers.get(index[(z+1)/2]).getIconUrl());
                        }
                        else
                        { Log.d("Icon odd",Integer.toString(index.length));
                            tempWeather.setIconUrl(weathers.get(index[((z+1)/2)+1]).getIconUrl());
                        }

                        Log.d("Icon setted",tempWeather.getIconUrl());
                        for(int l=0;l<temp.size();l++)
                        {
                            String[] previousDate = temp.get(l).getTime().split(" ");
                            String[] currentDate = tempWeather.getTime().split(" ");
                            if(previousDate[0].equals(currentDate[0]))
                            {
                                temp.set(l,tempWeather);
                            }
                        }
                    }
                    count=0;
                    flag=true;
                }
                TextView threeHourlyForecastCity_tv = (TextView) findViewById(R.id.dailyForecast_tv);
                threeHourlyForecastCity_tv.setText("Daily Forecast for "+ weathers.get(0).getCityName()+","+weathers.get(0).getStateName());
                    setTopRecycler(temp);
                super.onPostExecute(weathers);
            }
        }
    }

    public void setTopRecycler(List<Weather> temp)
    {

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(CityWeatherActivity.this,LinearLayoutManager.HORIZONTAL,false);

        final RecyclerView rvWeathers = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        rvWeathers.setLayoutManager(horizontalLayoutManager);
        WeatherRecyclerAdapter adapter = new WeatherRecyclerAdapter(CityWeatherActivity.this, temp,rvWeathers,weathersList);
        rvWeathers.setAdapter(adapter);
        rvWeathers.setLongClickable(true);
        Log.d("demo","inside fav recycler adapter");
        final int pos = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvWeathers.findViewHolderForAdapterPosition(pos).itemView.performClick();
            }
        },1);
    }

    }

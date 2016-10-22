package com.example.raviteja.weatherapp;

/*
* Homework 05
* Nanda Kishore Kolluru
* Ravi Teja Yarlagadda
* */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBDataManager dm;
    String temperatureUnit="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Weather App");
        setContentView(R.layout.activity_main);
       dm = new DBDataManager(this);
        List<Cities> cities = dm.getAllCities();
        Log.d("demo",Integer.toString(cities.size()));
        Log.d("demo",cities.toString());
        final EditText cityName = (EditText) findViewById(R.id.CityName);
        final EditText stateName = (EditText) findViewById(R.id.StateName_et);
        TextView savedcities = (TextView) findViewById(R.id.savedcities);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.view2);
        setSupportActionBar(mtoolbar);
        Button submit = (Button) findViewById(R.id.Submit_button);
       final TextView nofav = (TextView) findViewById(R.id.no_favorites_mess_tv);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
        temperatureUnit=preferenceTempUnit;

        if (getIntent().getExtras()!=null) {
            Toast.makeText(MainActivity.this,"No cities match your query", Toast.LENGTH_SHORT).show();
        }
            if(cities.size()>0)
            {
                List<Cities> reorderFavs= new ArrayList<Cities>();
                for(int h=0;h<cities.size();h++)
                {
                    if(cities.get(h).getFavorite().equals("true"))
                    {
                        reorderFavs.add(cities.get(h));
                    }
                }
                for(int g=0;g<cities.size();g++)
                {
                    if(cities.get(g).getFavorite().equals("false"))
                    {
                        reorderFavs.add(cities.get(g));
                    }
                }
                savedcities.setText("Saved Cities");
                RecyclerView rvWeathers = (RecyclerView) findViewById(R.id.SavedCitiesRecyclerLayout);
                FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(this, reorderFavs, rvWeathers);
                rvWeathers.setAdapter(adapter);
                Log.d("demo","inside fav recycler adapter");
                rvWeathers.setLayoutManager(new LinearLayoutManager(this));
            }
            else
            {
                nofav.setText("There is no city in your Favorites");
            }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CityWeatherActivity.class);
                intent.putExtra("CityName", cityName.getText());
                intent.putExtra("StateName", stateName.getText());
                Log.d("moved","done");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
        temperatureUnit=preferenceTempUnit;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.custom_menu,menu);
        return true;
    }


    public void updateFavs(List<Cities> cities) {
        List<Cities> reorderFavs= new ArrayList<Cities>();

        for(int i=0;i<cities.size();i++)
        {
            Log.d("favs cities received",cities.get(i).toString());
            if(cities.get(i).getFavorite().equals("true"))
            {
                reorderFavs.add(cities.get(i));
                Log.d("favs gold added", cities.get(i).toString() );
            }
        }

        for(int i=0;i<cities.size();i++)
        {
            Log.d("favs inside gray",cities.get(i).getFavorite());
            if(cities.get(i).getFavorite().equals("false"))
            {
                reorderFavs.add(cities.get(i));
                Log.d("favs gray added", cities.get(i).toString() );
            }
        }
        if(reorderFavs!=null)
        {
            RecyclerView rvWeathers = (RecyclerView) findViewById(R.id.SavedCitiesRecyclerLayout);
            FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(this, reorderFavs, rvWeathers);
            rvWeathers.setAdapter(adapter);
            Log.d("demo","inside fav recycler adapter after updated");
            rvWeathers.setLayoutManager(new LinearLayoutManager(this));
        }
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_setting:
                Intent intPrefs=new Intent(MainActivity.this,PrefsActivity.class);
                startActivity(intPrefs);
                return true;
        }
        return false;
    }
}

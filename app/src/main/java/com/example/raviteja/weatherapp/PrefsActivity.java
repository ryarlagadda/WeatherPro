package com.example.raviteja.weatherapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by RAVITEJA on 10/21/2016.
 */
public class PrefsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, final Preference preference) {
            if(preference.getKey().equals("Temperature"))
            {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Select Temperature Unit");
                builder.setCancelable(false);
                builder.setItems(R.array.keyword, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String [] str=getResources().getStringArray(R.array.keyword);
                        getActivity().finish();
                        String unicodeF="\u2109";
                        if(str[which].equals(unicodeF) )
                        {
                            Toast.makeText(getActivity(), "Temperature Unit has been changed to °F", Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(getResources().getString(R.string.Temperature),"F");
                            editor.commit();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Temperature Unit has been changed to °C", Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(getResources().getString(R.string.Temperature),"C");
                            editor.commit();
                        }
                    }
                });
                builder.create().show();
                Log.d("demo","Shared Pref value");
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}

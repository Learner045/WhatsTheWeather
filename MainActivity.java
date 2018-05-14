package com.example.converter.com.theweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

import data.JSONWeatherParser;
import data.WeatherHttpClient;
import data.cityPreference;
import model.Weather;
import util.Utils;

public class MainActivity extends AppCompatActivity {

    private TextView cityName,temp,description,humidity,pressure,win,sunset,sunrise,updated;
    ImageView iconimage;


    Weather weather=new Weather();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



        cityName=(TextView)findViewById(R.id.cityText);
        iconimage=(ImageView)findViewById(R.id.thumbnailIcon);
        temp=(TextView)findViewById(R.id.temptext);
        description=(TextView)findViewById(R.id.cloudtext);
        humidity=(TextView)findViewById(R.id.humidtext);
        pressure=(TextView)findViewById(R.id.pressuretext);
        win=(TextView)findViewById(R.id.windtext);
        sunrise=(TextView)findViewById(R.id.risetext);
        sunset=(TextView)findViewById(R.id.settext);
        updated=(TextView)findViewById(R.id.updatetext);

        cityPreference cp=new cityPreference(MainActivity.this);

        renderWeatherData(cp.getCity());

        

    }


    public void renderWeatherData(String city){


        WeatherTask weatherTask=new WeatherTask();
        weatherTask.execute(new String[]{city+"&units=metric"});

    }


    private class DownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap>{

        URL url;
        HttpURLConnection urlConnection=null;
        InputStream is;

        @Override
        protected Bitmap doInBackground(String... strings) {

            return downloadimage(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            iconimage.setImageBitmap(bitmap);
        }

        private Bitmap downloadimage(String code){


            try {
                url = new URL(Utils.ICON_URL + code + ".png");
//                url = new URL(Utils.ICON_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();

                if(statusCode != HttpURLConnection.HTTP_OK){
                    Log.e("DownloadImage", "Error" + statusCode + urlConnection.getResponseMessage());
                    return null;
                }

                is = new BufferedInputStream(urlConnection.getInputStream());

                // Decode contents from the InputStream
                return BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return null;
        } // end downloadImage




    }




    private class WeatherTask extends AsyncTask<String,Void, Weather>{

        //while data is being downloaded user shudnt kept waiting so do it in background
        @Override
        protected Weather doInBackground(String... strings) {
            String data=((new WeatherHttpClient()).getWeatherData(strings[0]));
            weather= JSONWeatherParser.getWeather(data);
            Log.v("data",weather.place.getCity());
            weather.iconData = weather.currentCondition.getIcon();


            return weather;
        }

        //data gathered is populated here
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            String a= Utils.makeprecise(weather.place.getSunrise());
            String b=Utils.makeprecise(weather.place.getSunset());
            String c=Utils.makeprecise(weather.place.getLastupdate());

            DecimalFormat dc=new DecimalFormat("#.#");
            String k=dc.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.place.getCity()+","+weather.place.getCountry());
            temp.setText(""+k+"C");
            humidity.setText("Humidity"+weather.currentCondition.getHumidity()+"%");
            pressure.setText("Pressure:"+weather.currentCondition.getPressure());
            win.setText("Wind:"+weather.wind.getSpeed()+"m/s");
            sunrise.setText("Sunrise:"+a);
            sunset.setText("Sunset"+b);
            updated.setText("Last updated:"+c);
            description.setText("Condition"+weather.currentCondition.getCondition()+" ("+weather.currentCondition.getDescription()+" )");



        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}

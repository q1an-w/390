package com.example.app_390.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_390.R;
import com.example.app_390.settings.SettingsLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherController { //create controller object, call getWeatherDetails and then getter functions for each attribute

    Context context;
    private final float DEVICE_LAT= 45; //setting this from device db
    private final float DEVICE_LONG= -73; //setting this from device db
    private final String url = "https://api.openweathermap.org/data/3.0/onecall?";
    private final String appid = "fc7a4edfd8e28b98fb6adeb06ff3fb06";

    public double temperature;
    protected float longitude,lattitude;
    public int humidity;
    public String weather;
    public String description;

    protected TextView Temperature_text;
    protected TextView icon;
    protected TextView weathertype_text;
    protected TextView Humidity_text;
    protected TextView lattitude_text;
    protected TextView longitude_text;
    protected TextView description_text;

    public WeatherController(Context context,TextView temperature, TextView icon, TextView weathertype, TextView Humidity, TextView lattitude, TextView longitude, TextView description) {
        super();
        this.context=context;
        Temperature_text=temperature;
        this.icon=icon;
        weathertype_text=weathertype;
        Humidity_text=Humidity;
        lattitude_text=lattitude;
        longitude_text=longitude;
        description_text=description;
    }

    public double getTemp(){
        return temperature;
    }

    public String getWeather(){
        return weather;
    }

    public int getHumidity(){return humidity;}
    public String getDescription(){return description;}

    public void getWeatherDetails(){  //Montreal lattitude and longitude: 45.508888, -73.561668.
        //API call to request current weather and forecast data:
        //https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&appid={API key}
        updateCoordinates();
      String tempURL= url + "lat=" + lattitude + "&lon=" + longitude + "&appid=" + appid + "&exclude=minutely,hourly,daily,alerts&units=metric";

      StringRequest stringRequest = new StringRequest(Request.Method.GET, tempURL, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              Log.d("response",response);
              String output = "";
              try{
                  JSONObject jsonResponse = new JSONObject(response);
                  JSONObject jsonCurrent = jsonResponse.getJSONObject("current");
                  double temp = jsonCurrent.getDouble("temp"); //get the temperature in degrees
                  int humidity_percent = jsonCurrent.getInt("humidity");
                  JSONArray jsonweather = jsonCurrent.getJSONArray("weather");
                  JSONObject weatherinfo = jsonweather.getJSONObject(0);
                  String weather_type = weatherinfo.getString("main");
                  String weather_description = weatherinfo.getString("description");
                  temperature=temp;
                  weather=weather_type;
                  description=weather_description;
                  humidity=humidity_percent;
                  Temperature_text.setText(Math.round(temp)+" °C");
                  weathertype_text.setText(weather_type);
                  description_text.setText(description);
                  Humidity_text.setText("Humidity: " + humidity + "%");
                  updateIcon(weather_type);
                  //set lattitude and longitude based on options
              } catch (JSONException e){
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              Toast.makeText(context,error.toString().trim(),Toast.LENGTH_SHORT).show();
          }

    });
        RequestQueue requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void updateIcon(String weather){
        switch (weather) {
            case "Thunderstorm":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thunderstorm, 0, 0, 0);
                break;
            case "Drizzle":
            case "Rain":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rain, 0, 0, 0);
                break;
            case "Snow":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.snow, 0, 0, 0);
                break;
            case "Mist":
            case "Smoke":
            case "Haze":
            case "Dust":
            case "Fog":
            case "Sand":
            case "Ash":
            case "Squall":
            case "Tornado":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fog, 0, 0, 0);
                break;
            case "Clear":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clear, 0, 0, 0);
                break;
            case "Clouds":
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clouds, 0, 0, 0);
                break;
        }
    }

    protected void hideWeather(){
        Temperature_text.setVisibility(View.INVISIBLE);
        icon.setVisibility(View.INVISIBLE);
        weathertype_text.setVisibility(View.INVISIBLE);
        Humidity_text.setVisibility(View.INVISIBLE);
        lattitude_text.setVisibility(View.INVISIBLE);
        longitude_text.setVisibility(View.INVISIBLE);
        description_text.setVisibility(View.INVISIBLE);
    }

    protected void updateCoordinates(){
        SharedPreferences coordinates = context.getSharedPreferences("coordinates",Context.MODE_PRIVATE);
        this.lattitude=coordinates.getFloat("Latitude", DEVICE_LAT);
        this.longitude=coordinates.getFloat("Longitude",DEVICE_LONG);
        lattitude_text.setText("Lattitude: " + Float.toString(lattitude));
        longitude_text.setText("Longitude: " + Float.toString(longitude));
    }

}

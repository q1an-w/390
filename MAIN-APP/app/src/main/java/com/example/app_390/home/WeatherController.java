package com.example.app_390.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherController { //create controller object, call getWeatherDetails and then getter functions for each attribute

    Context context;
    private final String url = "https://api.openweathermap.org/data/3.0/onecall?";
    private final String appid = "fc7a4edfd8e28b98fb6adeb06ff3fb06";

    public double temperature;
    public int humidity;
    public String weather;

    public WeatherController(Context context) {
        super();
        this.context=context;
    }

    public double getTemp(){
        return temperature;
    }

    public String getWeather(){
        return weather;
    }

    public int getHumidity(){
        return humidity;

    }
    public void getWeatherDetails(){  //Montreal lattitude and longitude: 45.508888, -73.561668.
        //API call to request current weather and forecast data:
        //https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&appid={API key}
      String tempURL= url + "lat=45.51&lon=-73.56&appid=" + appid + "&exclude=minutely,hourly,daily,alerts&units=metric";

      StringRequest stringRequest = new StringRequest(Request.Method.GET, tempURL, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              Log.d("response",response);
              String output = "";
              try{
                  JSONObject jsonResponse = new JSONObject(response);
                  JSONObject jsonCurrent = jsonResponse.getJSONObject("current");
                  double temp = jsonCurrent.getDouble("temp"); //get the temperature in degrees
                  JSONArray jsonweather = jsonCurrent.getJSONArray("weather");
                  JSONObject weatherinfo = jsonweather.getJSONObject(0);
                  String weather_type = weatherinfo.getString("main");
                  //get all the necessary info from jsonObjectMain
                  //double temp = jsonObjectMain.getDouble("temp");
                  output="Current temperature" + Double.toString(temp) + " , " + weather_type;
                  Toast.makeText(context,output,Toast.LENGTH_LONG).show();
                  temperature=temp;
                  weather=weather_type;

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


}

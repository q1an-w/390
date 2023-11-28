package com.example.app_390.database;

import android.content.Context;
import android.content.SharedPreferences;

public class AppMemory {
    private SharedPreferences sp;
    public AppMemory(Context context)
    {
        sp = context.getSharedPreferences("main", Context.MODE_PRIVATE );
    }
    public void clear(){
        SharedPreferences.Editor cleaner = sp.edit();
        cleaner.clear();
        cleaner.apply();
    }
    public void clearPwd(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password","");
        editor.apply();
    }

    public void saveSignup(String username, String password,String deviceID){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("deviceID",deviceID);
        editor.apply();
    }

    public boolean checkSavedLogin(){
        if(getSavedLoginUsername().equals("") && getSavedLoginPassword().equals("")){
            return false;
        }else{
            return true;
        }

    }
    public String getSavedLoginUsername(){
        return sp.getString("username","").toString();

    }
    public String getSavedLoginPassword(){
        return sp.getString("password","").toString();

    }
    public String getSavedLoginDeviceID(){
        return sp.getString("deviceID","").toString();
    }

    public void savePreferences(boolean enableEmail, boolean enableWeather, boolean enableVoice) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("enableEmail",enableEmail);
        editor.putBoolean("enableWeather",enableWeather);
        editor.putBoolean("enableVoice",enableVoice);
        editor.apply();
    }
    public boolean isEmailEnabled(){
        return sp.getBoolean("enableEmail",false);
    }
    public boolean isWeatherEnabled(){
        return sp.getBoolean("enableWeather",false);
    }
    public boolean isVoiceEnabled(){
        return sp.getBoolean("enableVoice",false);
    }
    public void saveOptionSelection(boolean showLow, boolean showMedium, boolean showHigh,
                                    boolean showToday, boolean showWeek, boolean showMonth) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("showLow", showLow);
        editor.putBoolean("showMedium", showMedium);
        editor.putBoolean("showHigh", showHigh);
        editor.putBoolean("showToday", showToday);
        editor.putBoolean("showWeek", showWeek);
        editor.putBoolean("showMonth", showMonth);
        editor.apply();
    }
    public boolean isShowLow() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showLow", false);
    }

    public boolean isShowMedium() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showMedium", false);
    }

    public boolean isShowHigh() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showHigh", false);
    }

    public boolean isShowToday() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showToday", false);
    }

    public boolean isShowWeek() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showWeek", false);
    }

    public boolean isShowMonth() {
        // Replace "sp" with your actual SharedPreferences instance
        return sp.getBoolean("showMonth", false);
    }

}

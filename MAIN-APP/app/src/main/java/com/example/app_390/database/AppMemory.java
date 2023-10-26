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

}

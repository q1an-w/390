package com.example.app_390.database;

import android.widget.TextView;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public interface MyDataCallback {
    public void dataCallback(Class c,String[] arr);
    public void dataCallback(TextView f, TextView l, WaveProgressBar w, Class c, String[] arr);
    public void resetDataList();
}

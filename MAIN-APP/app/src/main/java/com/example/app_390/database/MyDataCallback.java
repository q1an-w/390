package com.example.app_390.database;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public interface MyDataCallback {
    public void dataCallback(Class c,String[] arr);
    public void dataCallback(WaveProgressBar w, Class c, String[] arr);
    public void resetDataList();
}

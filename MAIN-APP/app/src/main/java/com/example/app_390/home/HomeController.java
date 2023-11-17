package com.example.app_390.home;

import static java.lang.Double.parseDouble;
import static java.lang.Math.round;

import android.view.Menu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyDataCallback;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class HomeController {
    protected ScrollView notif;
    protected WaveProgressBar levelFlowIndicator;
    protected TextView flow;
    protected TextView level;

    protected TextView weatherapi;
    private Menu menu;
    private AppMemory appMemory;
    private FirebaseController FC;

    public HomeController(ScrollView notif, WaveProgressBar levelFlowIndicator, TextView flow, TextView level, TextView weatherapi, Menu menu, AppMemory appMemory, FirebaseController FC) {
        this.notif = notif;
        this.levelFlowIndicator = levelFlowIndicator;
        this.flow = flow;
        this.level = level;
        this.weatherapi = weatherapi;
        this.menu = menu;
        this.appMemory = appMemory;
        this.FC = FC;
    }

    public void signout(){
        appMemory.clear();

    }

    public void setLevelFlowIndicator(){
        //LEVEL: value from 0 to 100 to set elevation
        //FLOW: slowest = 4000, speed increases as value decreases

        FC.getNewestData(appMemory, this.flow,this.level, this.levelFlowIndicator, new MyDataCallback() {

            @Override
            public void dataCallback(Class c, String[] arr, boolean prev) {

            }

            @Override
            public void dataCallback(TextView f, TextView l, WaveProgressBar w, Class c, String[] arr) {
                w.setWaveDuration(convertFlow(arr[3]));
                w.setProgress(convertLevel(arr[2]));
//                w.setProgress(50);
                f.setText("FLOW: " + arr[5] );
                l.setText("LEVEL: " + arr[4]);

            }

            @Override
            public void resetDataList() {}
        });
    }
    private int convertFlow(String number){
        double num = parseDouble(number);

        int inputRange = 300;
        int outputRange = 4000;
        int scaleFactor = outputRange / inputRange;
        double result = outputRange - (num * scaleFactor);

        return (int) round(result);



    }
    private int convertLevel(String number){
        Integer num = Integer.parseInt(number);

        int inputRange = 100;
        int outputRange = 100;
        int scaleFactor = outputRange / inputRange;
        int result =(num * scaleFactor);

        return result;



    }

}

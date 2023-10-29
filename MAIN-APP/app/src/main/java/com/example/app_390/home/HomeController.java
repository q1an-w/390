package com.example.app_390.home;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_390.database.AppMemory;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class HomeController {
    protected TextView notif;
    protected WaveProgressBar levelFlowIndicator;
    protected Button historyButton;
    protected TextView weatherapi;
    private Menu menu;
    private AppMemory appMemory;

    public HomeController(TextView notif, WaveProgressBar levelFlowIndicator, Button historyButton, TextView weatherapi, Menu menu, AppMemory appMemory) {
        this.notif = notif;
        this.levelFlowIndicator = levelFlowIndicator;
        this.historyButton = historyButton;
        this.weatherapi = weatherapi;
        this.menu = menu;
        this.appMemory = appMemory;
    }

    public void signout(){
        appMemory.clear();

    }

    public void setLevelFlowIndicator(int level, int flow){
        //LEVEL: value from 0 to 100 to set elevation
        //FLOW: slowest = 4000, speed increases as value decreases
        this.levelFlowIndicator.setWaveDuration(flow);
        this.levelFlowIndicator.setProgress(level);
    }
}

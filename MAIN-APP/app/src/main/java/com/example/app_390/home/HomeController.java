package com.example.app_390.home;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_390.database.AppMemory;

public class HomeController {
    protected TextView notif;
    protected TextView levelFlowIndicator;
    protected Button historyButton;
    protected TextView weatherapi;
    private Menu menu;
    private AppMemory appMemory;

    public HomeController(TextView notif, TextView levelFlowIndicator, Button historyButton, TextView weatherapi, Menu menu, AppMemory appMemory) {
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

}

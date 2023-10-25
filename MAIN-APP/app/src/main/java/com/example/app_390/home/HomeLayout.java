package com.example.app_390.home;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;

public class HomeLayout extends AppCompatActivity {

    protected Button settingsButton;
    protected TextView homeTitle;
    protected TextView notif;
    protected TextView levelFlowIndicator;
    protected Button historyButton;
    protected TextView weatherapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initialViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.home_layout);
        initialViews();
    }

    private void initialViews(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Home Page");

        settingsButton = findViewById(R.id.settingsButton);
        homeTitle = findViewById(R.id.homeTitle);
        notif = findViewById(R.id.notif);
        levelFlowIndicator = findViewById(R.id.levelFlowIndicator);
        historyButton = findViewById(R.id.historyButton);
        weatherapi = findViewById(R.id.weatherapi);
    }
}

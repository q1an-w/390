package com.example.app_390.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;

public class HomeLayout extends AppCompatActivity {

    protected TextView notif;
    protected TextView levelFlowIndicator;
    protected Button historyButton;
    protected TextView weatherapi;
    private Menu menu;

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


        notif = findViewById(R.id.notif);
        levelFlowIndicator = findViewById(R.id.levelFlowIndicator);
        historyButton = findViewById(R.id.historyButton);
        weatherapi = findViewById(R.id.weatherapi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        //menu.findItem(R.id.menu_option).setTitle("edit");
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(true){
            menu.findItem(R.id.menu_option).setTitle("Save");
        }else{
            menu.findItem(R.id.menu_option).setTitle("Edit Settings");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.menu_option){
            if(true){

                invalidateOptionsMenu();
            }else{
                invalidateOptionsMenu();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.app_390.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;
import com.example.app_390.data.DataLayout;
import com.example.app_390.database.AppMemory;
import com.example.app_390.login.LoginLayout;
import com.example.app_390.settings.SettingsLayout;

import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class HomeLayout extends AppCompatActivity {

    int waterlevel=20;
    int waterflow=3000;
    protected TextView notif;
    protected WaveProgressBar levelFlowIndicator;
    protected Button historyButton;
    protected TextView weatherapi;
    private Menu menu;
    private AppMemory appMemory;
    private HomeController HC;

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
        String htmlTitle = "<font color=" + Color.parseColor("#FF6200EE")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF6200EE") + ">FLOW</font><font color="+Color.parseColor("#FFd3d3d3") + "> HOME</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle,1));
        notif = findViewById(R.id.notif);
        levelFlowIndicator = findViewById(R.id.levelflowindicator);
        Timer timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
            }
        };
        timer.schedule(timerTask,0,80);
        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(toDatapage);
        weatherapi = findViewById(R.id.weatherapi);
        appMemory = new AppMemory(HomeLayout.this);
        HC = new HomeController(notif, levelFlowIndicator,historyButton,weatherapi, menu,appMemory);
        HC.setLevelFlowIndicator(waterlevel,waterflow);
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
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.signout){
            HC.signout();
            finish();
        }
        else if(item.getItemId() == R.id.data){
            Intent intent = new Intent(HomeLayout.this, DataLayout.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.settings){
            Intent intent = new Intent(HomeLayout.this, SettingsLayout.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener toDatapage =  new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent intent = new Intent(getApplicationContext(), DataLayout.class);
            startActivity(intent);
        }
    };
}

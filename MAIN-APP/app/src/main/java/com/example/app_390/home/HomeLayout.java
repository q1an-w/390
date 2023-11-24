package com.example.app_390.home;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;
import com.example.app_390.data.DataLayout;
import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyNotificationsCallback;
import com.example.app_390.settings.SettingsLayout;

import java.util.Locale;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class HomeLayout extends AppCompatActivity {

    protected LinearLayout weatherwidget;
    protected TextView temperature;
    protected TextView icon;
    protected TextView weathertype;
    protected TextView Humidity;
    protected TextView lattitude;
    protected TextView longitude;
    protected TextView description;

    protected ScrollView notifs;
    protected WaveProgressBar levelFlowIndicator;
    protected TextView flow;
    protected TextView level;
    protected TextView weatherapi;

    private Menu menu;
    private AppMemory appMemory;
    private HomeController HC;

    private ImageButton buttonSpeech;
    private TextToSpeech textToSpeech;

    private FirebaseController FC;
    private TextView[] notif1;
    private TextView[] notif2;
    private TextView[] notif3;
    private TextView[] notif4;
    private TextView[] notif5;


    private WeatherController WC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        buttonSpeech= findViewById(R.id.buttonSpeech);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        // adding onlicklistenser for the speaky speaky
        buttonSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(flow.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        initialViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.home_layout);
        initialViews();
    }

    private void initialViews() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        String htmlTitle = "<font color=" + Color.parseColor("#FF6200EE")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF6200EE") + ">FLOW</font><font color=" + Color.parseColor("#FFd3d3d3") + "> HOME</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle, 1));
        CircleOverlay CO = new CircleOverlay(HomeLayout.this);
        FrameLayout fl = findViewById(R.id.framelayout);
        fl.addView(CO);
        levelFlowIndicator = findViewById(R.id.levelflowindicator);
        flow = findViewById(R.id.flowtextview);
        level = findViewById(R.id.leveltextview);
        notifs = findViewById(R.id.notifscroll);
        weatherwidget = findViewById(R.id.weatherlayout);
        setWeatherWidget();
        setNotifViews();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 80);
        levelFlowIndicator.setOnClickListener(toDatapage);
        appMemory = new AppMemory(HomeLayout.this);
        FC = new FirebaseController();
        HC = new HomeController(weatherwidget, levelFlowIndicator,flow,level, weatherapi, menu, appMemory, FC);
        WC = new WeatherController(getApplicationContext(),temperature, icon, weathertype, Humidity, lattitude, longitude, description);
        TextView t = findViewById(R.id.connection);
        HC.setLevelFlowIndicator(t);
        setPermissionsView(appMemory.isEmailEnabled(), appMemory.isWeatherEnabled(), appMemory.isVoiceEnabled());
        FC.getNotifications(appMemory,new MyNotificationsCallback(){

            @Override
            public void notifCallback(ArrayList<String[]> data) {
                setNotifs(data);
            }

            @Override
            public void resetDataList() {

            }
        });


    }
    private void setNotifs(ArrayList<String[]> data){

        String[] singlenotif1 = data.get(0);
        String[] singlenotif2 = data.get(1);
        String[] singlenotif3 = data.get(2);
        String[] singlenotif4 = data.get(3);
        String[] singlenotif5 = data.get(4);
        checkIfNullNotif(singlenotif1,notif1);
        checkIfNullNotif(singlenotif2,notif2);
        checkIfNullNotif(singlenotif3,notif3);
        checkIfNullNotif(singlenotif4,notif4);
        checkIfNullNotif(singlenotif5,notif5);



    }

    private void checkIfNullNotif(String[] notifData,TextView[] tvArr){
        if (notifData == null || notifData[0] == null || notifData[1] == null|| notifData[2] == null|| notifData[3] == null) {
            for (TextView tv : tvArr) {
                tv.setVisibility(View.GONE);
            }
        }else{

            for (TextView tv : tvArr) {
                tv.setVisibility(View.VISIBLE);
            }
            tvArr[1].setText(notifData[1]+"                           At: " + notifData[2]);
            tvArr[2].setText("Water Level at: " + notifData[0]+ "                 Water Flow at: " + notifData[3]);
            String importance = calculateImportance(notifData[4],notifData[5]);

            if(importance.matches("HIGH")){
                String html = "<font color=" + Color.parseColor("#FFFF0000")
                        + ">CHECK YOUR DRAIN <br></br> </font>It may be clogged";
                tvArr[0].setText(Html.fromHtml(html,1));

            }
            else if(importance.matches("MEDIUM")){
                String html = "<font color=" + Color.parseColor("#FFFF5F15")
                        + ">DRAIN WARNING <br></br> </font>Please monitor the situation";
                tvArr[0].setText(Html.fromHtml(html,1));
            }
            else if(importance.matches("LOW")){
//                String html = "<font color=" + Color.parseColor("#FFFF0000")
//                        + ">CHECK YOUR DRAIN <br></br> </font>It may be clogged";
//                tvArr[0].setText(Html.fromHtml(html,1));
                for (TextView tv : tvArr) {
                    tv.setVisibility(View.GONE);
                }
            }

        }
    }

    private String calculateImportance(String level_status, String rate_status) {
        String importance;
        if (level_status.matches("HIGH")) {
            importance = "HIGH";
        } else if (level_status.matches("MEDIUM") || (level_status.matches("LOW") && (rate_status.matches("NO FLOW") || rate_status.matches("FLOW")))) {
            importance = "MEDIUM";
        } else {
            importance = "LOW";
        }
        return importance;
    }
    private void setNotifViews(){
        notif1 = new TextView[3];
        notif2 = new TextView[3];
        notif3 = new TextView[3];
        notif4 = new TextView[3];
        notif5 = new TextView[3];

        ///1
        notif1[0] = findViewById(R.id.notifTitle1);
        notif1[1] = findViewById(R.id.notifDate1);
        notif1[2] = findViewById(R.id.notifImportance1);


        ///2
        notif2[0] = findViewById(R.id.notifTitle2);
        notif2[1] = findViewById(R.id.notifDate2);
        notif2[2] = findViewById(R.id.notifImportance2);

        ///3
        notif3[0] = findViewById(R.id.notifTitle3);
        notif3[1] = findViewById(R.id.notifDate3);
        notif3[2] = findViewById(R.id.notifImportance3);

        ///4
        notif4[0] = findViewById(R.id.notifTitle4);
        notif4[1] = findViewById(R.id.notifDate4);
        notif4[2] = findViewById(R.id.notifImportance4);

        ///5
        notif5[0] = findViewById(R.id.notifTitle5);
        notif5[1] = findViewById(R.id.notifDate5);
        notif5[2] = findViewById(R.id.notifImportance5);


    }

    private void setWeatherWidget(){
        temperature=findViewById(R.id.temperature);
        icon=findViewById(R.id.weathericon);
        weathertype=findViewById(R.id.weathertype);
        Humidity=findViewById(R.id.humidity);
        lattitude=findViewById(R.id.lattitude);
        longitude=findViewById(R.id.longitude);
        description=findViewById(R.id.weatherdescription);
    }




    private void setPermissionsView(boolean emailEnabled, boolean weatherEnabled, boolean voiceEnabled) {

        if(weatherEnabled){
            WC.getWeatherDetails();
        }else{
            WC.hideWeather();

        }
        if(voiceEnabled){

        }else{

        }
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


        if (item.getItemId() == R.id.signout) {
            HC.signout();
            finish();
        } else if (item.getItemId() == R.id.data) {
            Intent intent = new Intent(HomeLayout.this, DataLayout.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(HomeLayout.this, SettingsLayout.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener toDatapage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), DataLayout.class);
            startActivity(intent);
        }
    };
}

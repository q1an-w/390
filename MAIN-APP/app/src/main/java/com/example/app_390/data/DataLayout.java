package com.example.app_390.data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;
import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.database.MyDataCallback;
import com.example.app_390.home.HomeLayout;
import com.example.app_390.settings.SettingsLayout;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class  DataLayout extends AppCompatActivity {

    boolean testmode=false; //SET TO FALSE FOR DB data, SET TO TRUE FOR EXAMPLE DATA
    private TableLayout dataTable;
    private boolean[] optionselection;
    private Toolbar myToolbar;
    private DatePickerDialog datePickerDialog;
    private DataController data_control;
    private ScrollView datascroll;
    private Menu optionsmenu;
    private FirebaseController FC;
    private AppMemory appMemory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page_layout);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        optionselection = new boolean[6];
        for (int i=0;i<6;i++)
            optionselection[i]=false;
        setupUI();
        data_control=new DataController(dataTable);
        initialiseDatePicker();
        FC = new FirebaseController();
        appMemory = new AppMemory(this);


        if(testmode==false) {
            FC.getData(appMemory, new MyDataCallback() {
                @Override
                public void dataCallback(Class c, String[] arr, boolean sameAsPrevious) {
                    if(sameAsPrevious){
                        data_control.updateTimestamp(dataTable,arr);

                    }else data_control.tmpInsertData(dataTable, arr);

                }

                @Override
                public void dataCallback(TextView f, TextView l, WaveProgressBar w, Class c, String[] arr) {

                }

                @Override
                public void resetDataList() {
                    data_control.resetDataList(dataTable);
                }

            });
        }



        if (testmode==true) {
            //FOR TESTING PURPOSES (Uncomment this part AND comment out FC.getData(new MyDataCallback())
            String[] level_states={"DRY","LOW","MEDIUM","HIGH"};
            String[] rate_states={"NO FLOW","LOW","MEDIUM","HIGH"};
            String[] dataex = new String[6];
            dataex[0] = "2023-10-22";
            dataex[1] = "1:51 pm";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-10-05";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-10-11";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-05";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-11";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-13";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-13";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-14";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-15";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            dataex[4] = level_states[new Random().nextInt(4)];
            dataex[5] = rate_states[new Random().nextInt(4)];
            data_control.simpleInsertData(dataTable, dataex);
        }
    }

    private void initialiseDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1; //So that Jan=1
                String dayString=Integer.toString(day);
                String monthString=Integer.toString(month);
                if(day<10){
                    dayString="0"+day;
                }
                if(month<10){
                    monthString="0"+month;
                }
                String selected_date = year + "-" + monthString + "-" + dayString;
                data_control.showDate(dataTable,selected_date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,dateSetListener,year,month,day);

    }

    public void setupUI(){
        dataTable=findViewById(R.id.data_table);
        dataTable.setColumnStretchable(0,true);
        dataTable.setColumnStretchable(1,true);
        dataTable.setColumnStretchable(2,true);
        dataTable.setColumnStretchable(3,true);
        dataTable.setColumnStretchable(4,true);
        datascroll = findViewById(R.id.datascrollview);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String htmlTitle = "<font color=" + Color.parseColor("#04bf96")
                   + ">DRAIN</font><font color="
                   + Color.parseColor("#dbbffd") + ">FLOW</font><font color="+Color.parseColor("#ffffff") + "> MY DATA</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle,1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_actions, menu);
        optionsmenu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.Totop) {
            datascroll.fullScroll(ScrollView.FOCUS_UP);
            return true;
        }

        if (itemId == R.id.Showmedium) {
            setOptionSelection(false, true, false, false, false, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showlow) {
            setOptionSelection(true, false, false, false, false, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showhigh) {
            setOptionSelection(false, false, true, false, false, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showall) {
            data_control.showall(dataTable);
            setOptionSelection(false, false, false, false, false, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showtoday) {
            setOptionSelection(false, false, false, true, false, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showweek) {
            setOptionSelection(false, false, false, false, true, false);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Showmonth) {
            setOptionSelection(false, false, false, false, false, true);
            data_control.applyfilters(optionselection);
            return true;
        }

        if (itemId == R.id.Selectdate) {
            datePickerDialog.show();
            setOptionSelection(false, false, false, false, false, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void setOptionSelection(boolean showLow, boolean showMedium, boolean showHigh,
                                    boolean showToday, boolean showWeek, boolean showMonth) {
        optionselection[0] = showLow;
        optionselection[1] = showMedium;
        optionselection[2] = showHigh;
        optionselection[3] = showToday;
        optionselection[4] = showWeek;
        optionselection[5] = showMonth;
        setChecks(optionselection);
    }

    private void setChecks(boolean[] select){
        MenuItem showlow = optionsmenu.findItem(R.id.Showlow);
        MenuItem showmed = optionsmenu.findItem(R.id.Showmedium);
        MenuItem showhigh = optionsmenu.findItem(R.id.Showhigh);
        MenuItem showtoday = optionsmenu.findItem(R.id.Showtoday);
        MenuItem showweek = optionsmenu.findItem(R.id.Showweek);
        MenuItem showmonth = optionsmenu.findItem(R.id.Showmonth);

        if (showlow != null) showlow.setChecked(select[0]);
        if (showmed != null) showmed.setChecked(select[1]);
        if (showhigh != null) showhigh.setChecked(select[2]);
        if (showtoday != null) showtoday.setChecked(select[3]);
        if (showweek != null) showweek.setChecked(select[4]);
        if (showmonth != null) showmonth.setChecked(select[5]);
    }


}

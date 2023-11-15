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
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.R;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.database.MyDataCallback;
import com.example.app_390.home.HomeLayout;
import com.example.app_390.settings.SettingsLayout;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class  DataLayout extends AppCompatActivity {

    boolean testmode=true; //SET TO FALSE FOR DB data, SET TO TRUE FOR EXAMPLE DATA
    private TableLayout dataTable;
    private boolean[] optionselection;
    private Toolbar myToolbar;
    private DatePickerDialog datePickerDialog;
    private DataController data_control;
    private ScrollView datascroll;
    private Menu optionsmenu;
    private FirebaseController FC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page_layout);
        optionselection = new boolean[6];
        for (int i=0;i<6;i++)
            optionselection[i]=false;
        setupUI();
        data_control=new DataController(dataTable);
        initialiseDatePicker();
        FC = new FirebaseController();


        if(testmode==false) {
            FC.getData(new MyDataCallback() {
                @Override
                public void dataCallback(Class c, String[] arr) {
                    data_control.tmpInsertData(dataTable, arr);
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
            String[] dataex = new String[4];
            dataex[0] = "2023-10-22";
            dataex[1] = "1:51 pm";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-10-05";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-10-11";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-05";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-07";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-11";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-13";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-13";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-14";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
            data_control.simpleInsertData(dataTable, dataex);
            dataex[0] = "2023-11-15";
            dataex[2] = String.valueOf(new Random().nextInt(10));
            dataex[3] = String.valueOf(new Random().nextInt(150));
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
        String htmlTitle = "<font color=" + Color.parseColor("#FF6200EE")
                   + ">DRAIN</font><font color="
                   + Color.parseColor("#FF6200EE") + ">FLOW</font><font color="+Color.parseColor("#FFd3d3d3") + "> MY DATA</font>";
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
        if (item.getItemId() == R.id.Totop)
            datascroll.fullScroll(ScrollView.FOCUS_UP);
        if (item.getItemId() == R.id.Showmedium) {
            optionselection[1]=true;
            optionselection[0]=false;
            optionselection[2]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showlow) {
            optionselection[0]=true;
            optionselection[1]=false;
            optionselection[2]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showhigh) {
            optionselection[2]=true;
            optionselection[1]=false;
            optionselection[0]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showall) {
            data_control.showall(dataTable);
            for (int i=0;i<6;i++)
                optionselection[i]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showtoday){
            optionselection[3]=true;
            optionselection[4]=false;
            optionselection[5]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showmonth){
            optionselection[5]=true;
            optionselection[3]=false;
            optionselection[4]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Showweek){
            optionselection[4]=true;
            optionselection[5]=false;
            optionselection[3]=false;
            setChecks(optionselection);
            data_control.applyfilters(optionselection);
        }
        if (item.getItemId() == R.id.Selectdate){
            datePickerDialog.show();
            for (int i=0;i<6;i++)
                optionselection[i]=false;
            setChecks(optionselection);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setChecks(boolean[] select){
        MenuItem showlow = optionsmenu.getItem(3);
        MenuItem showmed = optionsmenu.getItem(4);
        MenuItem showhigh = optionsmenu.getItem(5);
        MenuItem showtoday = optionsmenu.getItem(6);
        MenuItem showweek = optionsmenu.getItem(7);
        MenuItem showmonth = optionsmenu.getItem(8);
        showlow.setChecked(select[0]);
        showmed.setChecked(select[1]);
        showhigh.setChecked(select[2]);
        showtoday.setChecked(select[3]);
        showweek.setChecked(select[4]);
        showmonth.setChecked(select[5]);
    }


}

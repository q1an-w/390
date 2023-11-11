package com.example.app_390.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.Random;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class  DataLayout extends AppCompatActivity {

    private TableLayout dataTable;
    private Toolbar myToolbar;
    private DataController data_control;
    private ScrollView datascroll;
    private Menu optionsmenu;
    private FirebaseController FC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page_layout);
        setupUI();
        data_control=new DataController();

        FC = new FirebaseController();

/*
        FC.getData(new MyDataCallback() {
            @Override
            public void dataCallback(Class c, String[] arr) {
                data_control.tmpInsertData(dataTable,arr);
            }

            @Override
            public void dataCallback(TextView f, TextView l, WaveProgressBar w, Class c, String[] arr){

            }

            @Override
            public void resetDataList(){
                data_control.resetDataList(dataTable);
            }

        });
*/

        //FOR TESTING PURPOSES (Uncomment this part AND comment out FC.getData(new MyDataCallback())
        String[] dataex = new String[4];
        dataex[0] = "2023-10-22";
        dataex[1] = "1:51 pm";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-10-05";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-10-11";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-05";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-07";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-07";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-07";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-11";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);
        dataex[0] = "2023-11-11";
        dataex[2] = String.valueOf(new Random().nextInt(20));
        dataex[3] = String.valueOf(new Random().nextInt(150));
        data_control.simpleInsertData(dataTable, dataex);

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
        boolean[] select = new boolean[6];
        for (int i=0;i<6;i++)
            select[i]=false;
        if (item.getItemId() == R.id.Totop)
            datascroll.fullScroll(ScrollView.FOCUS_UP);
        if (item.getItemId() == R.id.Showmedium) {
            data_control.showimportance(dataTable, "MEDIUM");
            select[1]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showlow) {
            data_control.showimportance(dataTable, "LOW");
            select[0]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showhigh) {
            data_control.showimportance(dataTable, "HIGH");
            select[2]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showall) {
            data_control.showall(dataTable);
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showtoday){
            data_control.showDate(dataTable,data_control.getDate());
            select[3]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showmonth){
            data_control.showMonth(dataTable,data_control.getMonth());
            select[5]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Showweek){
            data_control.showWeek(dataTable,data_control.getWeek());
            select[4]=true;
            setChecks(select);
        }
        if (item.getItemId() == R.id.Selectdate){

        }
        return super.onOptionsItemSelected(item);
    }

    public void setChecks(boolean[] select){
        MenuItem showmed = optionsmenu.getItem(3);
        MenuItem showlow = optionsmenu.getItem(2);
        MenuItem showhigh = optionsmenu.getItem(4);
        MenuItem showtoday = optionsmenu.getItem(5);
        MenuItem showweek = optionsmenu.getItem(6);
        MenuItem showmonth = optionsmenu.getItem(7);
        showlow.setChecked(select[0]);
        showmed.setChecked(select[1]);
        showhigh.setChecked(select[2]);
        showtoday.setChecked(select[3]);
        showweek.setChecked(select[4]);
        showmonth.setChecked(select[5]);
    }
}

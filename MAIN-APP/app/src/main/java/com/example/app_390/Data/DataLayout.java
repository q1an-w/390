package com.example.app_390.Data;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_390.R;

public class DataLayout extends AppCompatActivity {

    private TableLayout dataTable;
    private DataController data_control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page_layout);
        data_control=new DataController();
        setupUI();
        //data_control.updateData();
        String[] dataexample=new String[4];
        dataexample[0]="22/10/2023";
        dataexample[1]="1:51 pm";
        dataexample[2]="5";
        dataexample[3]="500";
        data_control.insertData(dataTable,dataexample);
        data_control.insertData(dataTable,dataexample);
        data_control.insertData(dataTable,dataexample);
        data_control.insertData(dataTable,dataexample);
        data_control.insertData(dataTable,dataexample);
        dataexample[3]="0";
        data_control.insertData(dataTable,dataexample);
    }

    void setupUI(){
    dataTable=findViewById(R.id.data_table);
    dataTable.setColumnStretchable(0,true);
    dataTable.setColumnStretchable(1,true);
    dataTable.setColumnStretchable(2,true);
    dataTable.setColumnStretchable(3,true);
    dataTable.setColumnStretchable(4,true);
    }
}

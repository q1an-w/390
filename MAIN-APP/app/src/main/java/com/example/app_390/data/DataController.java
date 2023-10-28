package com.example.app_390.data;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DataController{

    protected void insertData(TableLayout table, String[] data){
        TableRow row=new TableRow(table.getContext());
        String date = data[0];
        String time = data[1];
        String level = data[2];
        String flow = data[3];
        String importance=calculateImportance(level,flow);
        if(importance.matches("high"))
            row.setBackgroundColor(Color.parseColor("#b8474b"));
        else if (importance.matches("medium")) {
            row.setBackgroundColor(Color.parseColor("#ff9000"));
        }
        TextView col1=new TextView(table.getContext());
        col1.setText(date);
        col1.setGravity(Gravity.CENTER);
        TextView col2=new TextView(table.getContext());
        col2.setText(time);
        col2.setGravity(Gravity.CENTER);
        TextView col3=new TextView(table.getContext());
        col3.setText(level);
        col3.setGravity(Gravity.CENTER);
        TextView col4=new TextView(table.getContext());
        col4.setText(flow);
        col4.setGravity(Gravity.CENTER);
        TextView col5=new TextView(table.getContext());
        col5.setText(importance);
        col5.setGravity(Gravity.CENTER);
        if(importance.matches("high")) {
            col1.setTextColor(Color.parseColor("#ffffff"));
            col2.setTextColor(Color.parseColor("#ffffff"));
            col3.setTextColor(Color.parseColor("#ffffff"));
            col4.setTextColor(Color.parseColor("#ffffff"));
            col5.setTextColor(Color.parseColor("#ffffff"));
        }
        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);
        row.addView(col5);
        table.addView(row,1);
    }

    private String calculateImportance(String waterlevel, String waterflow){
        String importance;
        int mediumflow=5;
        int lowflow=1;
        int highlevel=10;
        int mediumlevel=5;
        if(Integer.valueOf(waterflow)<lowflow & Integer.valueOf(waterlevel)>highlevel)
            importance = "high";
        else if (Integer.valueOf(waterflow)<mediumflow & Integer.valueOf(waterlevel)>mediumlevel) {
            importance = "medium";
        }
        else importance="low";
        return importance;
    }

    protected void updateData(){
        //pull data from firebase and update list
    }
}

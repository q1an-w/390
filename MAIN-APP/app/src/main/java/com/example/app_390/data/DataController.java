package com.example.app_390.data;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataController{

    int numberofrows=0;
    protected void tmpInsertData(TableLayout table, String[] data){
        TableRow row=new TableRow(table.getContext());
        String[] date_time = formatDate(data[0]);
        String date = date_time[0];
        String time = date_time[1];
        String level = data[2];
        String flow = data[3];
        String importance=calculateImportance(level,flow);
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
        row.addView(col1);
        row.addView(col2);
        row.addView(col3);
        row.addView(col4);
        row.addView(col5);
        formatrow(col1,col2,col3,col4,col5,row,importance);
        table.addView(row,1);
        numberofrows++;
    }

//    protected void insertData(TableLayout table, String[] data){
//        TableRow row=new TableRow(table.getContext());
//        String date = data[0];
//        String time = data[1];
//        String level = data[2];
//        String flow = data[3];
////        String importance=calculateImportance(level,flow);
////        importance = "high";
////        if(importance.matches("high"))
////            row.setBackgroundColor(Color.parseColor("#b8474b"));
////        else if (importance.matches("medium")) {
////            row.setBackgroundColor(Color.parseColor("#ff9000"));
////        }
//        row.setBackground(Color.parseColor(calculateImportance(level,flow));
//        TextView col1=new TextView(table.getContext());
//        col1.setText(date);
//        col1.setGravity(Gravity.CENTER);
//        TextView col2=new TextView(table.getContext());
//        col2.setText(time);
//        col2.setGravity(Gravity.CENTER);
//        TextView col3=new TextView(table.getContext());
//        col3.setText(level);
//        col3.setGravity(Gravity.CENTER);
//        TextView col4=new TextView(table.getContext());
//        col4.setText(flow);
//        col4.setGravity(Gravity.CENTER);
//        TextView col5=new TextView(table.getContext());
//        col5.setText(importance);
//        col5.setGravity(Gravity.CENTER);
//        if(importance.matches("high")) {
//            col1.setTextColor(Color.parseColor("#ffffff"));
//            col2.setTextColor(Color.parseColor("#ffffff"));
//            col3.setTextColor(Color.parseColor("#ffffff"));
//            col4.setTextColor(Color.parseColor("#ffffff"));
//            col5.setTextColor(Color.parseColor("#ffffff"));
//        }
//        row.addView(col1);
//        row.addView(col2);
//        row.addView(col3);
//        row.addView(col4);
//        row.addView(col5);
//        table.addView(row,1);
//    }

    private String calculateImportance(String waterlevel, String waterflow){
        String importance;
        int lowFlow=5;
        int highFlow=10;
        int highlevel=4;
        int flow=Integer.parseInt(waterflow);
        int level=Integer.parseInt(waterlevel);
        if(flow<lowFlow && level>highlevel) //low flow and high level
            importance = "HIGH";
        else if (flow>highFlow && level>highlevel)  //high flow and high level (danger of flooding)
            importance = "MEDIUM";
        else
            importance="LOW"; //if high flow and low level or low flow and low level

        return importance;
    }

    protected void updateData(){
        //pull data from firebase and update list
    }
    public void resetDataList(TableLayout table){
        int childCount = table.getChildCount();
        table.removeViews(1, childCount - 1); // Start from index 1 to keep the first header row

    }

    protected String[] formatDate(String date_string) {
        String[] date_time = new String[2];
        SimpleDateFormat initialformatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
        try {
            Date basedate = initialformatter.parse(date_string);
            date_time[0] = dateformatter.format(basedate);
            date_time[1] = timeformatter.format(basedate);
        }
        catch (ParseException e){

        }
        return date_time;
    }

    protected void formatrow(TextView column1,TextView column2,TextView column3,TextView column4,TextView column5, TableRow row, String importance){
        if(importance.matches("HIGH")) {
            row.setBackgroundColor(Color.parseColor("#b8474b"));
            column1.setTextColor(Color.parseColor("#ffffff"));
            column2.setTextColor(Color.parseColor("#ffffff"));
            column3.setTextColor(Color.parseColor("#ffffff"));
            column4.setTextColor(Color.parseColor("#ffffff"));
            column5.setTextColor(Color.parseColor("#ffffff"));
        }
        else if (importance.matches("MEDIUM"))
           row.setBackgroundColor(Color.parseColor("#ff9000"));

    }

    protected void showimportance(){
        //go through all columns in the table and hide columns that doesnt match importance
    }
}

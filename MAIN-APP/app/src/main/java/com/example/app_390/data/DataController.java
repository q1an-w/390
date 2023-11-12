package com.example.app_390.data;

import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;

public class DataController{

    int numberofrows=0;
    TableLayout datatable;
    int highFlow=100;
    int lowFlow=50;
    int highlevel=4;

    public DataController(TableLayout datatable) {
        super();
        this.datatable=datatable;
    }

    protected void tmpInsertData(TableLayout table, String[] data){
        TableRow row=new TableRow(table.getContext());
        String[] date_time = formatDate(data[0]);
        String date = date_time[0];
        String time = date_time[1];
        String level = data[2];
        String flow = data[3];
        String level_state = data[4];
        String rate_state = data[5];
        String importance=calculateImportance(level_state,rate_state);
        TextView col1=new TextView(table.getContext());
        col1.setText(date);
        col1.setGravity(Gravity.CENTER);
        TextView col2=new TextView(table.getContext());
        col2.setText(time);
        col2.setGravity(Gravity.CENTER);
        TextView col3=new TextView(table.getContext());
        //col3.setText(level);
        col3.setText(levelImportance(Integer.valueOf(level)));
        col3.setGravity(Gravity.CENTER);
        TextView col4=new TextView(table.getContext());
        //col4.setText(flow);
        col4.setText(flowImportance(Integer.valueOf(flow)));
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
        row.setPadding(0,20,0,20);
        table.addView(row,1);
        numberofrows++;
    }

    protected String flowImportance(int flow){
        String flowImportance;
        if(flow>highFlow)
            flowImportance="HIGH";
        else if(flow<highFlow & flow>lowFlow)
            flowImportance="MEDIUM";
        else
            flowImportance="LOW";
        return flowImportance;
    }

    protected String levelImportance(int level){
        String levelImportance;
        if(level>=highlevel)
            levelImportance="HIGH";
        else levelImportance="LOW";
        return levelImportance;
    }

    protected void simpleInsertData(TableLayout table, String[] data){ //use for testing purposes
        TableRow row=new TableRow(table.getContext());
        String date = data[0];
        String time = data[1];
        String level = data[2];
        String flow = data[3];
        String importance=calculateImportancenew(level,flow);
        TextView col1=new TextView(table.getContext());
        col1.setText(date);
        col1.setGravity(Gravity.CENTER);
        TextView col2=new TextView(table.getContext());
        col2.setText(time);
        col2.setGravity(Gravity.CENTER);
        TextView col3=new TextView(table.getContext());
       // col3.setText(level);
        col3.setText(levelImportance(Integer.valueOf(level)));
        col3.setGravity(Gravity.CENTER);
        TextView col4=new TextView(table.getContext());
       // col4.setText(flow);
        col4.setText(flowImportance(Integer.valueOf(flow)));
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
        row.setPadding(0,20,0,20);
        table.addView(row,1);
        numberofrows++;
    }

    private String calculateImportance(String level_status, String rate_status){
        String importance;

        if((level_status.matches("HIGH")|| level_status.matches("MEDIUM")) && rate_status.matches("MEDIUM")) {

            importance = "HIGH";
        }
        else if(!rate_status.matches("HIGH")){
            importance = "MEDIUM";
        }
        else importance = "LOW";
        return importance;

    }

    private String calculateImportancenew(String waterlevel, String waterflow){
        String importance="";
        int i=0;
        String flow=flowImportance(Integer.parseInt(waterflow));
        String level=levelImportance(Integer.parseInt(waterlevel));
        if(level.matches("HIGH")){
            if(flow.matches("HIGH"))
                importance="MEDIUM";
            if(flow.matches("MEDIUM"))
                importance="HIGH";
            if(flow.matches("LOW"))
                importance="HIGH";
        }
        if(level.matches("LOW")){
            if(flow.matches("HIGH"))
                importance="MEDIUM";
            if(flow.matches("MEDIUM"))
                importance="LOW";
            if(flow.matches("LOW"))
                importance="LOW";
        }
        return importance;
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
        else if (importance.matches("MEDIUM")){
            row.setBackgroundColor(Color.parseColor("#ff9000"));
            column1.setTextColor(Color.parseColor("#ffffff"));
            column2.setTextColor(Color.parseColor("#ffffff"));
            column3.setTextColor(Color.parseColor("#ffffff"));
            column4.setTextColor(Color.parseColor("#ffffff"));
            column5.setTextColor(Color.parseColor("#ffffff"));
        }
        else if (importance.matches("LOW")){
            column1.setTextColor(Color.parseColor("#000000"));
            column2.setTextColor(Color.parseColor("#000000"));
            column3.setTextColor(Color.parseColor("#000000"));
            column4.setTextColor(Color.parseColor("#000000"));
            column5.setTextColor(Color.parseColor("#000000"));

        }


    }

    protected void showimportance(TableLayout table, String importance){ //go through all columns in the table and hide columns that doesnt match importance
        for(int i =1;i<=numberofrows;i++){
            View view = table.getChildAt(i);
            TableRow r = (TableRow) view;
            //in this row (row i) of the table get the child element(column) where the first column would have a value of 0
            TextView getstatus = (TextView) r.getChildAt(4);
            String status = getstatus.getText().toString();
            if(r.getVisibility() == View.VISIBLE) {
                if (!status.matches(importance))
                    r.setVisibility(View.GONE);
                else
                    r.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void showDate(TableLayout table, String date){ //go through all columns in the table and hide columns that doesnt match the date given
        for(int i =1;i<=numberofrows;i++){
            View view = table.getChildAt(i);
            TableRow r = (TableRow) view;
            TextView getdate = (TextView) r.getChildAt(0);
            String entrydate = getdate.getText().toString();
            if(!entrydate.matches(date))
                r.setVisibility(View.GONE);
            else
                r.setVisibility(View.VISIBLE);
        }
    }

    protected void showMonth(TableLayout table, String month){ //go through all columns in the table and hide columns that doesnt match the date given
        for(int i =1;i<=numberofrows;i++){
            View view = table.getChildAt(i);
            TableRow r = (TableRow) view;
            TextView getdate = (TextView) r.getChildAt(0);
            String entrydate = getdate.getText().toString();
            String[] temp = entrydate.split("-");
            String entrymonth = temp[1];
            if(!entrymonth.matches(month))
                r.setVisibility(View.GONE);
            else
               r.setVisibility(View.VISIBLE);
        }
    }

    protected void showWeek(TableLayout table, ArrayList<String> weekdays){ //go through all columns in the table and hide columns that doesnt match the date given
        for(int i =1;i<=numberofrows;i++) {
            View view = table.getChildAt(i);
            TableRow r = (TableRow) view;
            TextView getdate = (TextView) r.getChildAt(0);
            String entrydate = getdate.getText().toString();
            r.setVisibility(View.GONE);
            for (int j=0;j<weekdays.size();j++){
            if (entrydate.matches(weekdays.get(j))) {
                r.setVisibility(View.VISIBLE);
                break;
             }
            }
        }
    }

    protected void showall(TableLayout table){ //Show all columns in data table
        for(int i =1;i<=numberofrows;i++){
            View view = table.getChildAt(i);
            TableRow r = (TableRow) view;
            r.setVisibility(View.VISIBLE);
        }
    }

    public String getDate(){ //returns today's date with yyyy-MM-dd format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date_string= formatter.format(date);
        return date_string;
    }
    public String getMonth(){ //returns current month
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date();
        String date_string= formatter.format(date);
        return date_string;
    }

    public ArrayList<String> getWeek(){ // returns a list of all the dates in the current week
        ArrayList<String> weekdates = new ArrayList<>();
        LocalDate localDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.now();

            LocalDate firstDayOfNextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            LocalDate firstDayOfThisWeek = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
            LocalDate temp = firstDayOfThisWeek;
            do {
                weekdates.add(String.valueOf(temp));
                temp = temp.plusDays(1);
            } while (temp.isBefore(firstDayOfNextWeek));
            return weekdates;
        }else return weekdates;
    }

    public void applyfilters(boolean[] options){
        showall(datatable);
        if(options[0]==false & options[1]==false & options[2]==false){//No importance selected
            if(options[3]) //show entries for today
                showDate(datatable,getDate());
            if(options[4]) //show entries for this week
                showWeek(datatable,getWeek());
            if(options[5]) //show entries for this month
                showMonth(datatable,getMonth());
        }
        if(options[0]){ //show low entries
            if(options[3]) //show low entries for today
                showDate(datatable,getDate());
            if(options[4]) //show low netries for this week
                showWeek(datatable,getWeek());
            if(options[5])
                showMonth(datatable,getMonth()); //show low entries for this month
            showimportance(datatable,"LOW");
        }
        if(options[1]){ //MEDIUM entries
            if(options[3]) //show medium entries for today
                showDate(datatable,getDate());
            if(options[4]) //show medium entries for this week
                showWeek(datatable,getWeek());
            if(options[5])
                showMonth(datatable,getMonth()); //show medium entries for this month
            showimportance(datatable,"MEDIUM");
        }
        if(options[2]){//HIGH entries
            if(options[3]) //show high entries for today
                showDate(datatable,getDate());
            if(options[4]) //show high entries for this week
                showWeek(datatable,getWeek());
            if(options[5])
                showMonth(datatable,getMonth()); //show high entries for this month
            showimportance(datatable,"HIGH");
        }
    }

    public void updateTimestamp(TableLayout dataTable, String[] arr) {
        TableRow row= (TableRow) dataTable.getChildAt(1);
        TextView col1 = (TextView) row.getChildAt(0);
        TextView col2 = (TextView) row.getChildAt(1);
        String[] date_time = formatDate(arr[0]);
        String date = date_time[0];
        String time = date_time[1];
        col1.setText(date);
        col2.setText(time);

    }
}

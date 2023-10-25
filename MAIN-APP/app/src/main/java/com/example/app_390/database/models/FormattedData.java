package com.example.app_390.database.models;

import java.util.Date;

public class FormattedData {
    private String date;
    private long time;
    private String water_level;
    private String water_flow;
    private String importance;

    public FormattedData(Boolean test){
        if(test){
            this.date = new Date().toString();
            this.time = new Date().getTime();
            this.water_level = "23";
            this.water_flow = "50";
            this.importance = "red";
        }else;
    }
    public FormattedData(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getWater_level() {
        return water_level;
    }

    public void setWater_level(String water_level) {
        this.water_level = water_level;
    }

    public String getWater_flow() {
        return water_flow;
    }

    public void setWater_flow(String water_flow) {
        this.water_flow = water_flow;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}

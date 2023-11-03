package com.example.app_390.database.models;

import java.util.ArrayList;


public class User {
    private String private_device_ID;
    private String username;
    private String password;



    public User(Boolean test){
        if(test){
            this.private_device_ID = "22:22:22222";
            this.username = "testuser";
            this.password = "testpwd";

        }else {

        }
    }
    public User(){
    }
    public User(String deviceID, String username, String password, ArrayList<String> raw_data, ArrayList<FormattedData> formatted_data){
        this.private_device_ID = deviceID;
        this.username = username;
        this.password = password;
    }

    public String getPrivate_device_ID() {
        return private_device_ID;
    }

    public void setPrivate_device_ID(String private_device_ID) {
        this.private_device_ID = private_device_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

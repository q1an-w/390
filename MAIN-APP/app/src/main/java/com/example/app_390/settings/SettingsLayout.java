package com.example.app_390.settings;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.home.HomeController;
import com.example.app_390.home.HomeLayout;
import com.example.app_390.login.LoginController;
import com.example.app_390.login.LoginLayout;
import com.example.app_390.R;

public class SettingsLayout extends AppCompatActivity {

    protected TextView username;
    protected EditText editUsername;
    protected TextView password;
    protected EditText editPassword;
    protected TextView deviceID;
    protected EditText editDeviceID;
    protected Button toggleEdit;
    private SettingsController SC;
    private FirebaseController FC;
    private AppMemory appMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        initialViews();
    }

    private void initialViews(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String htmlTitle = "<font color=" + Color.parseColor("#FF6200EE")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF6200EE") + ">FLOW</font><font color="+Color.parseColor("#FFd3d3d3") + "> SETTINGS</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle,1));

        FC = new FirebaseController();

        appMemory = new AppMemory(SettingsLayout.this);
        username = findViewById(R.id.username_settings);
        editUsername = findViewById(R.id.editUsername_settings);
        password = findViewById(R.id.password_settings);
        editPassword = findViewById(R.id.editPassword_settings);
        deviceID = findViewById(R.id.privateDeviceID_settings);
        editDeviceID = findViewById(R.id.editDeviceID_settings);
        toggleEdit = findViewById(R.id.toggleEdit);
        SC = new SettingsController(username,editUsername,password,editPassword,deviceID,editDeviceID,toggleEdit,SC,FC,appMemory);

        editUsername.setText(appMemory.getSavedLoginUsername());
        editPassword.setText(appMemory.getSavedLoginPassword());
        editDeviceID.setText(appMemory.getSavedLoginDeviceID());
        //implement auth check for settings
        editUsername.setEnabled(false);
        editPassword.setEnabled(false);
        editDeviceID.setEnabled(false);


    }
}

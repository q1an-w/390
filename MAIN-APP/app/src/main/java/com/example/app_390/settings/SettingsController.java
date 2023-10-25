package com.example.app_390.settings;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;

public class SettingsController {
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

    public SettingsController(TextView username, EditText editUsername, TextView password, EditText editPassword, TextView deviceID, EditText editDeviceID, Button toggleEdit, SettingsController SC, FirebaseController FC, AppMemory appMemory) {
        this.username = username;
        this.editUsername = editUsername;
        this.password = password;
        this.editPassword = editPassword;
        this.deviceID = deviceID;
        this.editDeviceID = editDeviceID;
        this.toggleEdit = toggleEdit;
        this.SC = SC;
        this.FC = FC;
        this.appMemory = appMemory;
    }
}

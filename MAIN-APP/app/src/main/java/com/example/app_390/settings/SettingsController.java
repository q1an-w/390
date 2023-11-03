package com.example.app_390.settings;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app_390.R;
import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.database.MyAuthCallback;
import com.example.app_390.home.HomeLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsController {
    protected TextView username;
    protected EditText editUsername;
    protected TextView password;
    protected EditText editPassword;
    protected TextView deviceID;
    protected EditText editDeviceID;
    protected Button toggleEdit;
    protected Button authenticate;
    protected EditText editPwdSetting;
    private SettingsController SC;
    private FirebaseController FC;
    private AppMemory appMemory;

    public SettingsController(TextView username, EditText editUsername, TextView password, EditText editPassword, TextView deviceID, EditText editDeviceID, Button toggleEdit, Button authenticate, EditText editPwdSetting, SettingsController SC, FirebaseController FC, AppMemory appMemory) {
        this.username = username;
        this.editUsername = editUsername;
        this.password = password;
        this.editPassword = editPassword;
        this.deviceID = deviceID;
        this.editDeviceID = editDeviceID;
        this.toggleEdit = toggleEdit;
        this.authenticate = authenticate;
        this.editPwdSetting = editPwdSetting;
        this.SC = SC;
        this.FC = FC;
        this.appMemory = appMemory;
    }
    public void validatePassword(String username, String pwd, MyActivityCallback cb){
        FC.loadAuthData(username, pwd,appMemory, new MyAuthCallback() {
            @Override
            public void authCallback(boolean auth) {
                if(auth){
                    cb.activityCallback(HomeLayout.class, true,false,"Success");
                }else{
                    cb.activityCallback(HomeLayout.class, false,false,"Authentication failed");
                }

            }
        });
    }

    public void saveInfo(String username, String pwd, String deviceID,MyActivityCallback cb){

        boolean validInputs = validateSignup(username, pwd, deviceID);
        FC.addUser(username,pwd,deviceID,cb,validInputs);
        FC.deleteOldUser(appMemory.getSavedLoginUsername());
        appMemory.saveSignup(username,pwd,deviceID);

    }
    private boolean validateSignup(String username, String pwd, String deviceID){
        if(username.equals("") || pwd.equals("") || deviceID.equals("") || !isStringInFormat(deviceID)){
            return false;
        }else return true;

    }
    private boolean isStringInFormat(String input) {
        // Define a regular expression pattern for the format XX:XX:XXXXX
        String pattern = "\\d{2}:\\d{2}:\\d{5}";

        // Create a Pattern object
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(input);

        // Check if the input string matches the pattern
        return matcher.matches();
    }

}

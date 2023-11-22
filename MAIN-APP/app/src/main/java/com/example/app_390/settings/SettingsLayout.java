package com.example.app_390.settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.R;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.login.LoginLayout;

public class SettingsLayout extends AppCompatActivity {

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
    private PopupWindow popupWindow;
    private Switch weatherNotif;
    private Switch voiceSupport;
    private Switch emailAlert;
    private boolean isWeather,isVoice,isEmail;


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
        weatherNotif = findViewById(R.id.drainweather);
        voiceSupport = findViewById(R.id.voicesupport);
        emailAlert = findViewById(R.id.emailnotif);

        SC = new SettingsController(username,editUsername,password,editPassword,deviceID,editDeviceID,toggleEdit,authenticate,editPwdSetting,SC,FC,appMemory);

        editUsername.setText(appMemory.getSavedLoginUsername());
        editPassword.setText(appMemory.getSavedLoginPassword());
        editDeviceID.setText(appMemory.getSavedLoginDeviceID());
        //implement auth check for settings
        editUsername.setEnabled(false);
        editPassword.setEnabled(false);
        editDeviceID.setEnabled(false);
        editDeviceID.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        toggleEdit.setText("edit");
        isWeather = appMemory.isWeatherEnabled();
        isVoice = appMemory.isVoiceEnabled();
        isEmail = appMemory.isEmailEnabled();
        weatherNotif.setChecked(appMemory.isWeatherEnabled());
        voiceSupport.setChecked(appMemory.isVoiceEnabled());
        emailAlert.setChecked(appMemory.isEmailEnabled());


        toggleEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEdit(view);
            }
        });
        weatherNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FC.savePreferences(isEmail, !isWeather, isVoice, appMemory);
                isWeather = !isWeather;

            }
        });
        voiceSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FC.savePreferences(isEmail, isWeather, !isVoice, appMemory);
                isVoice = !isVoice;
            }
        });
        emailAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FC.savePreferences(!isEmail, isWeather, isVoice, appMemory);
                isEmail = !isEmail;
            }
        });


    }
    public void toggleEdit(View view){
        if(toggleEdit.getText().toString().equals("edit")){


            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.pwd_popup, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            authenticate = popupView.findViewById(R.id.settingsValidate);
            editPwdSetting = popupView.findViewById(R.id.pwdCheck);
            authenticate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SC.validatePassword(editUsername.getText().toString(),editPwdSetting.getText().toString(),new MyActivityCallback(){
                        @Override
                        public void activityCallback(Class c, boolean auth, boolean validate_signup,String msg) {
                            if(auth){
                                editUsername.setEnabled(true);
                                editPassword.setEnabled(true);
                                editDeviceID.setEnabled(true);
                                toggleEdit.setText("save");
                                popupWindow.dismiss();
                                editDeviceID.setInputType(InputType.TYPE_CLASS_TEXT);
                            }else Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }else{
            SC.saveInfo(editUsername.getText().toString(), editPassword.getText().toString(), editDeviceID.getText().toString(), new MyActivityCallback() {
                @Override
                public void activityCallback(Class c, boolean auth, boolean valid_signup, String msg) {
                    if(auth){
                        editUsername.setEnabled(false);
                        editPassword.setEnabled(false);
                        editDeviceID.setEnabled(false);
                        toggleEdit.setText("edit");
                        editDeviceID.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    }else Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

package com.example.app_390.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.R;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.login.LoginLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    protected EditText setLattitude;
    protected EditText setLongitude;

    protected Button saveCoordinates;

    protected SharedPreferences coordinatesPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //coordinatesPreference = this.getPreferences(Context.MODE_PRIVATE);
        coordinatesPreference = getSharedPreferences("coordinates",MODE_PRIVATE);
        initialViews();
    }

    private void initialViews(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String htmlTitle = "<font color=" + Color.parseColor("#04bf96")
                + ">DRAIN</font><font color="
                + Color.parseColor("#dbbffd") + ">FLOW</font><font color="+Color.parseColor("#ffffff") + "> SETTINGS</font>";
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
                if(!emailAlert.isChecked()){
                    FC.savePreferences(!isEmail, isWeather, isVoice, appMemory);
                    isEmail = !isEmail;
                    FC.updateEmail(appMemory,"");
                }else{
                    inflateEmailPopup(view);
                }
            }
        });

        //SharedPreferences coordinatesPreference = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = coordinatesPreference.edit();

    }
    private void inflateEmailPopup(View view){
        emailAlert.setChecked(false);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.email_popup, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button saveEmail = popupView.findViewById(R.id.saveEmail);
        EditText email = popupView.findViewById(R.id.enterEmail);
        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isValidEmail(email.getText().toString())){
                    FC.savePreferences(!isEmail, isWeather, isVoice, appMemory);
                    isEmail = !isEmail;
                    FC.updateEmail(appMemory, email.getText().toString());
                    popupWindow.dismiss();
                    emailAlert.setChecked(true);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        // Define the regular expression pattern for a basic email format
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher with the provided email and the pattern
        Matcher matcher = pattern.matcher(email);

        // Return whether the email matches the pattern
        return matcher.matches();
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

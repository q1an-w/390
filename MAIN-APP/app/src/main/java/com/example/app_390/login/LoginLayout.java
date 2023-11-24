package com.example.app_390.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.R;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.database.MyPreferencesCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;


public class LoginLayout extends AppCompatActivity {

    protected TextView title;
    protected TextView toggle;
    protected TextView username;
    protected EditText editUsername;
    protected TextView password;
    protected EditText editPassword;
    protected TextView deviceID;
    protected EditText editDeviceID;
    protected Button auth;


    private LoginController LC;
    private FirebaseController FC;
    private AppMemory appMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.login_layout);
        create();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.login_layout);
        create();
    }
    private void create(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        String htmlTitle = "<font color=" + Color.parseColor("#05ECB9")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF3700B3") + ">FLOW</font><font color="+Color.parseColor("#ffffff") + "> AUTHENTICATION</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle,1));

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());

        FC = new FirebaseController();

        appMemory = new AppMemory(LoginLayout.this);
        toggle = findViewById(R.id.loginToggle);
        username = findViewById(R.id.username);
        editUsername = findViewById(R.id.editUsername);
        password = findViewById(R.id.password);
        editPassword = findViewById(R.id.editPassword);
        deviceID = findViewById(R.id.privateDeviceID);
        editDeviceID = findViewById(R.id.editDeviceID);
        auth = findViewById(R.id.authenticateUser);
        LC = new LoginController(title, toggle, username,editUsername,password,editPassword,deviceID,editDeviceID,auth,LC,FC,appMemory);


        LC.checkLoginHistory();
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {LC.toggleLogin();}
        });
//        title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FC.getData();
//            }
//        }
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {LC.auth(new MyActivityCallback() {
                @Override
                public void activityCallback(Class c, boolean auth, boolean validate_signup,String msg) {
                    if(auth){
                        if(validate_signup){
                            System.out.println(msg);
                            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = inflater.inflate(R.layout.consent_form_popup, null);
                            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            boolean focusable = true;
                            PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            Button accept = popupView.findViewById(R.id.accept);
                            Button cancel = popupView.findViewById(R.id.decline);
                            accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    appMemory.savePreferences(true,true,true);
                                    FC.savePreferences(true,true,true, appMemory);
                                    popupWindow.dismiss();
                                    Intent intent = new Intent(LoginLayout.this, c);
                                    startActivity(intent);

                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    appMemory.savePreferences(false,false,false);
                                    FC.savePreferences(false,false,false, appMemory);
                                    popupWindow.dismiss();
                                    Intent intent = new Intent(LoginLayout.this, c);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            FC.loadPreferences(new MyPreferencesCallback() {
                                @Override
                                public void savePrefs(boolean enableEmail, boolean enableWeather, boolean enableVoice) {
                                    appMemory.savePreferences(enableEmail,enableWeather,enableVoice);
                                    Intent intent = new Intent(LoginLayout.this, c);
                                    startActivity(intent);
                                }
                            }, appMemory);

                        }



                    } else if(!auth && validate_signup){
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        appMemory.clear();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });}
        });
    }

}

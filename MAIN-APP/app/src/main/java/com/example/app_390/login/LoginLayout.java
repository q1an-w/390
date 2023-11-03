package com.example.app_390.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.R;
import com.example.app_390.database.MyActivityCallback;
import com.example.app_390.database.MyAuthCallback;
import com.example.app_390.home.HomeLayout;

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
        String htmlTitle = "<font color=" + Color.parseColor("#FF6200EE")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF6200EE") + ">FLOW</font><font color="+Color.parseColor("#FFd3d3d3") + "> AUTHENTICATION</font>";
        getSupportActionBar().setTitle(Html.fromHtml(htmlTitle,1));


        FC = new FirebaseController();

        appMemory = new AppMemory(LoginLayout.this);
        title = findViewById(R.id.appTitle);
        toggle = findViewById(R.id.loginToggle);
        username = findViewById(R.id.username);
        editUsername = findViewById(R.id.editUsername);
        password = findViewById(R.id.password);
        editPassword = findViewById(R.id.editPassword);
        deviceID = findViewById(R.id.privateDeviceID);
        editDeviceID = findViewById(R.id.editDeviceID);
        auth = findViewById(R.id.authenticateUser);
        LC = new LoginController(title, toggle, username,editUsername,password,editPassword,deviceID,editDeviceID,auth,LC,FC,appMemory);


        String htmlTitleBig = "<font color=" + Color.parseColor("#FFBB86FC")
                + ">DRAIN</font><font color="
                + Color.parseColor("#FF6200EE") + ">FLOW</font><font color="+Color.WHITE + "> SOLUTIONS</font>";
        title.setText(Html.fromHtml(htmlTitleBig,1));
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
//        });
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {LC.auth(new MyActivityCallback() {
                @Override
                public void activityCallback(Class c, boolean auth, boolean validate_signup,String msg) {
                    if(auth){
                        System.out.println(msg);
                        Intent intent = new Intent(LoginLayout.this, c);
                        startActivity(intent);
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

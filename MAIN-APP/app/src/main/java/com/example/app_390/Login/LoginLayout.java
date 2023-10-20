package com.example.app_390.Login;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_390.DATABASE.AppMemory;
import com.example.app_390.DATABASE.FirebaseController;
import com.example.app_390.DATABASE.models.User;
import com.example.app_390.R;

public class LoginLayout extends AppCompatActivity {

    protected TextView title;
    protected Button toggle;
    protected TextView username;
    protected EditText editUsername;
    protected TextView password;
    protected EditText editPassword;
    protected TextView deviceID;
    protected EditText editDeviceID;
    protected Button login;
    protected TextView testDB;


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
        getSupportActionBar().setTitle("DrainFlow Login");


        FC = new FirebaseController();
        LC = new LoginController();
        appMemory = new AppMemory(LoginLayout.this);
        title = findViewById(R.id.appTitle);
        toggle = findViewById(R.id.loginToggle);
        username = findViewById(R.id.username);
        editUsername = findViewById(R.id.editUsername);
        password = findViewById(R.id.password);
        editPassword = findViewById(R.id.editPassword);
        deviceID = findViewById(R.id.privateDeviceID);
        editDeviceID = findViewById(R.id.editDeviceID);
        login = findViewById(R.id.authenticateUser);
        testDB = findViewById(R.id.testdb);





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });




    }
}

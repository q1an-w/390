package com.example.app_390.login;


import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_390.database.AppMemory;
import com.example.app_390.database.FirebaseController;
import com.example.app_390.database.MyCallback;

import java.util.Map;

public class LoginController {

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
    protected boolean isLogin;

    public LoginController(TextView title, TextView toggle, TextView username, EditText editUsername, TextView password, EditText editPassword, TextView deviceID, EditText editDeviceID, Button auth, LoginController LC, FirebaseController FC, AppMemory appMemory) {
        this.title = title;
        this.toggle = toggle;
        this.username = username;
        this.editUsername = editUsername;
        this.password = password;
        this.editPassword = editPassword;
        this.deviceID = deviceID;
        this.editDeviceID = editDeviceID;
        this.auth = auth;
        this.LC = LC;
        this.FC = FC;
        this.appMemory = appMemory;
    }

    public void toggleLogin(){
        //FC.testAddUser(new User(true));
        if(toggle.getText().equals("Sign Up")){
            isLogin = false;
            deviceID.setVisibility(View.VISIBLE);
            editDeviceID.setVisibility(View.VISIBLE);

            auth.setText("Sign up");
            toggle.setText("Login");
            ObjectAnimator animation0 = ObjectAnimator.ofFloat(toggle, "translationY", 0);

            ObjectAnimator animation = ObjectAnimator.ofFloat(auth, "translationY", 0);
            animation.setDuration(250);
            animation.start();
            animation0.setDuration(250);
            animation0.start();
        }else{
            isLogin = true;
            deviceID.setVisibility(View.GONE);
            editDeviceID.setVisibility(View.GONE);

            auth.setText("Login");
            toggle.setText("Sign Up");
            ObjectAnimator animation0 = ObjectAnimator.ofFloat(toggle, "translationY", -215);

            ObjectAnimator animation = ObjectAnimator.ofFloat(auth, "translationY", -215);
            animation0.setDuration(250);
            animation0.start();
            animation.setDuration(250);
            animation.start();
        }
    }
    public void auth(){
        String username = editUsername.getText().toString();
        String pwd = editPassword.getText().toString();
        if (isLogin){
            System.out.println(username + "   " + pwd);
            login(username,pwd);
        }else{
            signup(username, pwd);
        }
    }
    public void signup(String username, String pwd){
        System.out.println(FC.getAccess());

    }
    public void login(String username, String pwd){
        FC.loadAuthData(username, pwd, new MyCallback() {
            @Override
            public void onCallback(boolean auth) {
                if(auth){
                    System.out.println("yes");
                }else{
                    System.out.println("no");
                }

            }
        });
    }
}

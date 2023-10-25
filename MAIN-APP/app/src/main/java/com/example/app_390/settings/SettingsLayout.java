package com.example.app_390.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.app_390.Home.HomeLayout;
import com.example.app_390.Login.LoginLayout;
import com.example.app_390.R;

public class SettingsLayout extends Activity {

    private EditText edtUsername, edtPassword;
    private Button btnBack, btnSave, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);


        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsLayout.this, HomeLayout.class);
                startActivity(intent);

                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(!username.isEmpty() && !password.isEmpty()) {
                    Toast.makeText(SettingsLayout.this, "Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsLayout.this, "Please fill in both fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsLayout.this, LoginLayout.class);
                startActivity(intent);

                finish();
            }
        });
    }
}

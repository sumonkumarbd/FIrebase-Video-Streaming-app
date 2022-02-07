package com.sumonkmr.sharingmomments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Authenticator;

public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);


        signup.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,Signup.class));
        });

        login.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,Login.class));
        });




    }
}
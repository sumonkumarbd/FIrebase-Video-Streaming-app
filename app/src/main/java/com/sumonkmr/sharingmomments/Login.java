package com.sumonkmr.sharingmomments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button login;
    ProgressBar loading;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loading = findViewById(R.id.loading);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(Login.this, Dashbord.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginM(username,password);
            }
        });


    }

    public void LoginM(EditText username, View view)
    {
        loading.setVisibility(View.VISIBLE);
        String email= username.getText().toString();
        String passwords = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, passwords)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loading.setVisibility(View.INVISIBLE);
                            Intent intent=new Intent(Login.this,Dashbord.class);
                            intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        } else
                        {
                            loading.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),task.toString(),Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });



    }


}
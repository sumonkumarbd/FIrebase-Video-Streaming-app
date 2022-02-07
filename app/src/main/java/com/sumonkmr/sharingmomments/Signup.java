package com.sumonkmr.sharingmomments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Signup extends AppCompatActivity {
    
    EditText username, password;
    Button signup;
    ProgressBar loading;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loading = findViewById(R.id.loading);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(view -> signupM(username,password));


    }

    public void signupM(EditText username, View view)
    {
        loading.setVisibility(View.VISIBLE);
        String email= username.getText().toString();
        String passwords = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, passwords)
                .addOnCompleteListener(Signup.this, task -> {
                    if (task.isSuccessful())
                    {
                        loading.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(Signup.this,Dashbord.class);
                        intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                        intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                        startActivity(intent);
                    } else
                    {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),task.toString(),Toast.LENGTH_LONG).show();
                    }

                    // ...
                });



    }


}
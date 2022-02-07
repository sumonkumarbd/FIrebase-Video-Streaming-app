package com.sumonkmr.sharingmomments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Dashbord extends AppCompatActivity {

    RecyclerView rec_view;
    com.google.android.material.floatingactionbutton.FloatingActionButton floating_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        rec_view = findViewById(R.id.rec_view);
        floating_btn = findViewById(R.id.floating_btn);

        rec_view.setLayoutManager(new LinearLayoutManager(this));

        floating_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),AddVideos.class));
        });








    }
}
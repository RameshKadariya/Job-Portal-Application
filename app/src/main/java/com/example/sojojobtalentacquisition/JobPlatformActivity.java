package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;


public class JobPlatformActivity extends AppCompatActivity {
    ImageView jobProvider, jobSeeker,aboutMe, accountDetails;
    Toolbar toolbar;
    ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_platform);

        aboutMe=findViewById(R.id.realProfileImage);
        accountDetails=findViewById(R.id.accountDetailsImage);
        logout=findViewById(R.id.logoutButton);
        jobProvider=findViewById(R.id.jobProviderImage);
        jobSeeker=findViewById(R.id.jobSeekerImage);
        toolbar=findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sojo Job Platform");

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutmeintent = new Intent(JobPlatformActivity.this, aboutme
                        .class);
                startActivity(aboutmeintent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutintent = new Intent(JobPlatformActivity.this, UserLogin.class);
                startActivity(logoutintent);
                finish();
            }
        });


        jobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobseekerintent = new Intent(JobPlatformActivity.this, JobSeeker.class);
                startActivity(jobseekerintent);
            }
        });

        jobProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobproviderintent = new Intent(JobPlatformActivity.this, JobProvider.class);
                startActivity(jobproviderintent);

            }
        });

    }
}
// In insertdataJobProvider.java
package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sojojobtalentacquisition.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class insertdataJobProvider extends AppCompatActivity {
    Toolbar toolbar;
    EditText job_title, job_description, job_skills, job_salary;
    Button postJob;

    FirebaseAuth firebaseAuth;
    DatabaseReference mJobPost;
    DatabaseReference mPublicDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insertdata_job_provider);

        toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sojo Job Platform-Post Your Job");


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        String uId = mUser.getUid();

        mJobPost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);
        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public database");

        job_title = findViewById(R.id.jobTitle);
        job_description = findViewById(R.id.jobDescription);
        job_skills = findViewById(R.id.jobskill);
        job_salary = findViewById(R.id.jobSalary);

        postJob = findViewById(R.id.btnPostJob);

        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobTitle = job_title.getText().toString().trim();
                String jobDescription = job_description.getText().toString().trim();
                String jobSkills = job_skills.getText().toString().trim();
                String jobSalary = job_salary.getText().toString().trim();

                if (jobTitle.isEmpty()) {
                    job_title.setError("Please Enter Job Title");
                    job_title.requestFocus();
                } else if (jobDescription.isEmpty()) {
                    job_description.setError("Please Enter Job Description");
                    job_description.requestFocus();
                } else if (jobSalary.isEmpty()) {
                    job_salary.setError("Please Enter Job Salary");
                    job_salary.requestFocus();
                } else if (jobSkills.isEmpty()) {
                    job_skills.setError("Please Enter Job Skills");
                    job_skills.requestFocus();
                }
                
                    String id = mJobPost.push().getKey();
                    String date = DateFormat.getDateInstance().format(new Date());

                    Data data = new Data(jobTitle, jobDescription, jobSkills, jobSalary, id, date);
                    mJobPost.child(id).setValue(data);
                    mPublicDatabase.child(id).setValue(data);
                    Toast.makeText(insertdataJobProvider.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(insertdataJobProvider.this, JobProvider.class));

            }
        });
    }
}
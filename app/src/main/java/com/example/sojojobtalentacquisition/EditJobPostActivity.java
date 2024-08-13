package com.example.sojojobtalentacquisition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sojojobtalentacquisition.Model.Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference.CompletionListener;

public class EditJobPostActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText jobTitle, jobDescription, jobSkills, jobSalary;
    Button btnSave;
    DatabaseReference jobPostDatabase;
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_job_post);

        toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Job Post");

        jobTitle = findViewById(R.id.editJobTitle);
        jobDescription = findViewById(R.id.editJobDescription);
        jobSkills = findViewById(R.id.editJobSkills);
        jobSalary = findViewById(R.id.editJobSalary);
        btnSave = findViewById(R.id.btnSaveJob);

        postId = getIntent().getStringExtra("postId");
        jobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(postId);

        // Fetch and populate job details
        jobPostDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data jobPost = dataSnapshot.getValue(Data.class);
                if (jobPost != null) {
                    jobTitle.setText(jobPost.getTitle());
                    jobDescription.setText(jobPost.getDescription());
                    jobSkills.setText(jobPost.getSkills());
                    jobSalary.setText(jobPost.getSalary());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditJobPostActivity.this, "Failed to load job post.", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = jobTitle.getText().toString().trim();
                String description = jobDescription.getText().toString().trim();
                String skills = jobSkills.getText().toString().trim();
                String salary = jobSalary.getText().toString().trim();

                if (!title.isEmpty() && !description.isEmpty() && !skills.isEmpty() && !salary.isEmpty()) {
                    jobPostDatabase.child("title").setValue(title, new CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError error, DatabaseReference ref) {
                            if (error == null) {
                                jobPostDatabase.child("description").setValue(description);
                                jobPostDatabase.child("skills").setValue(skills);
                                jobPostDatabase.child("salary").setValue(salary);

                                Toast.makeText(EditJobPostActivity.this, "Job post updated successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditJobPostActivity.this, "Failed to update job post.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EditJobPostActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.sojojobtalentacquisition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sojojobtalentacquisition.Model.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import java.util.HashMap;
import java.util.Map;

public class EditJobPostActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText jobTitle, jobDescription, jobSkills, jobSalary;
   // Button btnSave;
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
        //btnSave = findViewById(R.id.btnSaveJob);

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

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String title = jobTitle.getText().toString().trim();
////                String description = jobDescription.getText().toString().trim();
////                String skills = jobSkills.getText().toString().trim();
////                String salary = jobSalary.getText().toString().trim();
//
//                Map<String, Object> map = new HashMap<>();
//                map.put("title", jobTitle.getText().toString());
//                map.put("description", jobDescription.getText().toString());
//                map.put("skills", jobSkills.getText().toString());
//                map.put("salary", jobSalary.getText().toString());
//
//                jobPostDatabase.updateChildren(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                // Update was successful
//                                Toast.makeText(EditJobPostActivity.this, "Job post updated successfully.", Toast.LENGTH_SHORT).show();
//
//                                // Re-fetch the updated data and update the UI
//                                jobPostDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        // Fetch the updated job post data
//                                        JobProvider updatedJobPost = dataSnapshot.getValue(JobProvider.class);
//
//                                        // Update your UI here with the new data
//                                        // e.g., refresh your RecyclerView adapter or other UI components
//                                        // Assuming you have an adapter to display the job posts:
//                                       Data updatedPost = dataSnapshot.getValue(Data.class);
//                                        if (updatedPost != null) {
//                                            jobTitle.setText(updatedPost.getTitle());
//                                            jobDescription.setText(updatedPost.getDescription());
//                                            jobSkills.setText(updatedPost.getSkills());
//                                            jobSalary.setText(updatedPost.getSalary());
//                                        }
//
//
//
//
//                                        // Finish the activity if you want to return to the previous screen
//                                        finish();
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//                                        // Handle possible errors
//                                    }
//                                });
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Update failed
//                                Toast.makeText(EditJobPostActivity.this, "Failed to update job post.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });

    }



}

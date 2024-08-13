package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sojojobtalentacquisition.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobProvider extends AppCompatActivity {
    FloatingActionButton btnfloat;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    DatabaseReference JobPostDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_provider);

        btnfloat = findViewById(R.id.fab);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        String uId = mUser.getUid();

        JobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);
        recyclerView = findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent floatintent = new Intent(JobProvider.this, insertdataJobProvider.class);
                startActivity(floatintent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase, Data.class)
                .build();
        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(MyViewHolder holder, int position, Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDate(model.getDate());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Implement edit functionality
                        Intent intent = new Intent(JobProvider.this, EditJobPostActivity.class);
                        intent.putExtra("postId", getRef(position).getKey());
                        startActivity(intent);
                    }
                });
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Implement delete functionality
                        getRef(position).removeValue();
                    }
                });
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View myview;
        Button btnEdit, btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            myview = itemView;
            btnEdit = myview.findViewById(R.id.btnEdit);
            btnDelete = myview.findViewById(R.id.btnDelete);
        }

        public void setJobTitle(String title) {
            TextView mTitle = myview.findViewById(R.id.jobTitle);
            mTitle.setText(title);
        }

        public void setJobDate(String date) {
            TextView mDate = myview.findViewById(R.id.jobDate);
            mDate.setText(date);
        }

        public void setJobDescription(String description) {
            TextView mDescription = myview.findViewById(R.id.jobDescription);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills) {
            TextView mSkills = myview.findViewById(R.id.jobSkills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary) {
            TextView mSalary = myview.findViewById(R.id.jobSalary);
            mSalary.setText(salary);
        }
    }
}
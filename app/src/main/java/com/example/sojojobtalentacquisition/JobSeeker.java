package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sojojobtalentacquisition.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobSeeker extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;


   DatabaseReference mAllJobPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_seeker);



        toolbar = findViewById(R.id.alljobpostToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Job Post");


        recyclerView = findViewById(R.id.recyclerAllJob);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //new database creating public
        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public database");
        mAllJobPost.keepSynced(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mAllJobPost, Data.class)
                .build();
        FirebaseRecyclerAdapter<Data, AllJobPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobPostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AllJobPostViewHolder holder, int position, @NonNull Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());

                holder.btnjobapply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(JobSeeker.this, jobApplicationActivity.class);
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost, parent, false);
                return new AllJobPostViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{
        Button btnjobapply;


       View mView;



        public AllJobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnjobapply = mView.findViewById(R.id.allJobPostApllyButton);
        }



        public void setJobTitle(String title){
            TextView mTitle = mView.findViewById(R.id.alljobTitle);
            mTitle.setText(title);
        }
        public void setJobDescription(String description){
            TextView mDescription = mView.findViewById(R.id.alljobDescription);
            mDescription.setText(description);
        }
        public void setJobDate(String date){
            TextView mDate = mView.findViewById(R.id.alljobDate);
            mDate.setText(date);
        }
        public void setJobSkills(String skills){
            TextView mSkills = mView.findViewById(R.id.alljobSkills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = mView.findViewById(R.id.alljobSalary);
            mSalary.setText(salary);
        }


    }




}
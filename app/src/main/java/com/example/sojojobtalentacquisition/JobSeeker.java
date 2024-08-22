package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sojojobtalentacquisition.Model.Data;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobSeeker extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseReference mAllJobPost;
    FirebaseRecyclerPagingAdapter<Data, AllJobPostViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Add DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public database");
        mAllJobPost.keepSynced(true);

        setupAdapter();
    }

    private void setupAdapter() {
        PagingConfig config = new PagingConfig(
                10, // Page size
                5,  // Prefetch distance
                false // Enable placeholders
        );

        DatabasePagingOptions<Data> options = new DatabasePagingOptions.Builder<Data>()
                .setLifecycleOwner(this)
                .setQuery(mAllJobPost, config, Data.class)
                .build();

        adapter = new FirebaseRecyclerPagingAdapter<Data, AllJobPostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AllJobPostViewHolder holder, int position, @NonNull Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());

                holder.btnjobapply.setOnClickListener(v -> {
                    Intent intent = new Intent(JobSeeker.this, jobApplicationActivity.class);
                    startActivity(intent);
                });
            }

            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost, parent, false);
                return new AllJobPostViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder {
        Button btnjobapply;
        View mView;

        public AllJobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            btnjobapply = mView.findViewById(R.id.allJobPostApllyButton);
        }

        public void setJobTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.alljobTitle);
            mTitle.setText(title);
        }

        public void setJobDescription(String description) {
            TextView mDescription = mView.findViewById(R.id.alljobDescription);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills) {
            TextView mSkills = mView.findViewById(R.id.alljobSkills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary) {
            TextView mSalary = mView.findViewById(R.id.alljobSalary);
            mSalary.setText(salary);
        }
    }
}

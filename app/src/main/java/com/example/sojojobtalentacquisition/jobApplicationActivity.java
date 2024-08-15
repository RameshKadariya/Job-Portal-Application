package com.example.sojojobtalentacquisition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class jobApplicationActivity extends AppCompatActivity {
    EditText fullName, address, contactDetail, emailAddress;
    Button submitApplication;
    DatabaseReference applicationsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_application);

        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        contactDetail = findViewById(R.id.contactDetail);
        emailAddress = findViewById(R.id.emailAddress);
        submitApplication = findViewById(R.id.submitApplication);

        applicationsDatabase = FirebaseDatabase.getInstance().getReference().child("Applications");

        submitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String contact = contactDetail.getText().toString().trim();
                String email = emailAddress.getText().toString().trim();

                if (name.isEmpty() || addr.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    Toast.makeText(jobApplicationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String id = applicationsDatabase.push().getKey();
                com.example.sojojobtalentacquisition.Model.ApplicationData applicationData = new com.example.sojojobtalentacquisition.Model.ApplicationData(name, addr, contact, email, id);
                applicationsDatabase.child(id).setValue(applicationData);
                Toast.makeText(jobApplicationActivity.this, "Application  Submitted. The Company will contact you shortly.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
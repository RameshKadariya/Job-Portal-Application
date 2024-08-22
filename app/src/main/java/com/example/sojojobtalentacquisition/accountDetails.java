package com.example.sojojobtalentacquisition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sojojobtalentacquisition.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountDetails extends AppCompatActivity {

    EditText userName, userEmail, userPassword;
    DatabaseReference userDatabase;
    String userId;
    Button btnresetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        btnresetPassword = findViewById(R.id.resetPasswordButton);

        userId = getIntent().getStringExtra("userId");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }





        // Fetch and populate user details
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    userName.setText(user.getName());
                    userEmail.setText(user.getEmail());
                    userPassword.setText(user.getPassword());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(accountDetails.this, "Failed to load user details.", Toast.LENGTH_SHORT).show();
            }
        });


        btnresetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(userEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(accountDetails.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(accountDetails.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}
package com.example.sojojobtalentacquisition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sojojobtalentacquisition.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegister extends AppCompatActivity {
    Button btnregister;
    TextView txtlogin;
    EditText fullname, email, password, cpassword;
    ProgressBar registerprogress;
    FirebaseAuth firebaseAuth;
    DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_register);

        btnregister = findViewById(R.id.btn_register);
        txtlogin = findViewById(R.id.txt_signin);
        fullname = findViewById(R.id.txt_fullname);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        cpassword = findViewById(R.id.txt_cpassword);
        registerprogress = findViewById(R.id.userRegisterProgressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fullname = fullname.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Cpassword = cpassword.getText().toString().trim();

                if (Fullname.isEmpty()) {
                    Toast.makeText(UserRegister.this, "Please Enter Your Fullname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Email.isEmpty()) {
                    Toast.makeText(UserRegister.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(UserRegister.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Cpassword.equals(Password)) {
                    Toast.makeText(UserRegister.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        String userId = firebaseAuth.getCurrentUser().getUid();
                                                        User newUser = new User(Fullname, Email, Password);
                                                        userDatabase.child(userId).setValue(newUser)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(UserRegister.this, "Registration Successful. Please Verify Your Email", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            Toast.makeText(UserRegister.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        registerprogress.setVisibility(View.VISIBLE);
                                                        Toast.makeText(UserRegister.this, "Failed to request verification mail", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(UserRegister.this, "Registration Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(UserRegister.this, UserLogin.class);
                startActivity(loginintent);
            }
        });
    }
}
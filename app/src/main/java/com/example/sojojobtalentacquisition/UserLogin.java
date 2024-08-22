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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {
    TextView forgotPassword, register;
    EditText email,password;
    Button btnlogin;
    FirebaseAuth firebaseAuth;
    ProgressBar loginprogress;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);

        forgotPassword=findViewById(R.id.txt_forgot_password);
        register=findViewById(R.id.txt_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnlogin=findViewById(R.id.btn_login);
        loginprogress=findViewById(R.id.userLoginProgressbar);
        firebaseAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password =password.getText().toString().trim();

                if(Email.isEmpty()){
                    Toast.makeText(UserLogin.this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                }

                if(Password.isEmpty()){
                    Toast.makeText(UserLogin.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        userId= firebaseAuth.getCurrentUser().getUid();
                                        Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), JobPlatformActivity.class);
                                        intent.putExtra("userId", userId);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        loginprogress.setVisibility(View.VISIBLE);
                                        Toast.makeText(UserLogin.this, "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(UserLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotintent = new Intent(UserLogin.this, ForgotPassword.class);
                startActivity(forgotintent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(UserLogin.this, UserRegister.class);
                startActivity(registerintent);
            }
        });
    }
}
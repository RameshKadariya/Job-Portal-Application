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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    TextView backLogin;
    FirebaseAuth firebaseAuth;
    ProgressBar forgetPasswordProgressbar;
    EditText email;
    Button btnForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        backLogin=findViewById(R.id.txt_back_to_login);
        email=findViewById(R.id.fpemail);
        btnForgetPassword=findViewById(R.id.btn_reset_password);
        firebaseAuth=FirebaseAuth.getInstance();
        forgetPasswordProgressbar=findViewById(R.id.forgetPasswordProgressbar);

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();

                if(Email.isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Please Enter the email", Toast.LENGTH_SHORT).show();
                }
                else {
                    forgetPasswordProgressbar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent= new Intent(getApplicationContext(), UserLogin.class);
                                startActivity(intent);
                                Toast.makeText(ForgotPassword.this, "Check Your email", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ForgotPassword.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(ForgotPassword.this, UserLogin.class);
                startActivity(backintent);
            }
        });
    }
}
package com.example.babybuyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText InputEmail,InputPassword;
    TextView txtSignup,txtlogin;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputEmail=findViewById(R.id.InputEmail);
        InputPassword=findViewById(R.id.InputPassword);
        txtSignup=findViewById(R.id.txtSignup);
        txtlogin=findViewById(R.id.txtLogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,signup.class);
                startActivity(intent);
            }
        });

txtlogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
PerformLogin();
    }
});

    }

    private void PerformLogin() {

        String Email=InputEmail.getText().toString();
        String Password=InputPassword.getText().toString();
        if(Email.isEmpty())
        {
            InputEmail.setError("Enter Email");
        }
        else if(Password.isEmpty()||Password.length()<6)
        {
            InputPassword.setError("Enter Proper Password");
        }

        else {
            progressDialog.setMessage("Please Wait while Login...");
            progressDialog.setTitle("Logging in");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(login.this, "Login Successful", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();


                    }
                }
            });


        }
        }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(login.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
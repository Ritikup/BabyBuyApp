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

public class signup extends AppCompatActivity {
EditText InputEmail,InputPassword,InputConfPassword,InputName;
TextView txtLogin,txtSignup;
ProgressDialog progressDialog;
FirebaseAuth mAuth;
FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        InputEmail=findViewById(R.id.editTextEmail);
        InputName=findViewById(R.id.editTextName);
        InputPassword=findViewById(R.id.editTextPassword);
        InputConfPassword=findViewById(R.id.editTextConfPassword);
        txtSignup=findViewById(R.id.txtSignup);
        txtLogin=findViewById(R.id.txtLogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this,login.class);
                startActivity(intent);

            }
        });

txtSignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PerformAuth();
    }
});



    }

    private void PerformAuth() {
        String Email=InputEmail.getText().toString();
        String Password=InputPassword.getText().toString();
        String ConfPassword=InputConfPassword.getText().toString();
        if(Email.isEmpty())
        {
            InputEmail.setError("Enter Email");
        }
        else if(Password.isEmpty()||Password.length()<6)
        {
            InputPassword.setError("Enter Proper Password");
        }
        else if(ConfPassword.isEmpty()||Password.length()<6)
        {
            InputPassword.setError("Enter Proper Conform Password");
        }
        else if(!Password.equals(ConfPassword))
        {
            InputConfPassword.setError("Passwords Do not Match");
        }
        else{
            progressDialog.setMessage("Please Wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(signup.this, ""+task.getException(), Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(signup.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    }
}
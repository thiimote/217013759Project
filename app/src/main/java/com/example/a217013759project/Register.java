package com.example.a217013759project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText userEmail , userPassword;
    private Button Submit ;
    private TextView haveAnAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog loodingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        loodingBar = new ProgressDialog(this);
        userEmail = (EditText)findViewById(R.id.registerEmail);
        userPassword = (EditText)findViewById(R.id.registerPassword);
        Submit = (Button)findViewById(R.id.submitRegister);
        haveAnAccount = (TextView)findViewById(R.id.haveAnAccount);

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount()
    {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter Email.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter Password...", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loodingBar.setTitle("creating new Account");
                loodingBar.setMessage("Please wait, while creating new account for you!");
                loodingBar.setCanceledOnTouchOutside(true);
                loodingBar.show();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                      if (task.isSuccessful())
                      {
                          Intent intent = new Intent(Register.this,MainActivity.class);
                          startActivity(intent);
                          Toast.makeText(Register.this, "Account created successfuly", Toast.LENGTH_SHORT).show();
                          loodingBar.dismiss();
                      }
                      else
                          {
                              String message = task.getException().toString();
                              Toast.makeText(Register.this, "Error:" +message, Toast.LENGTH_SHORT).show();
                              loodingBar.dismiss();
                          }
                    }
                });
            }
    }


}

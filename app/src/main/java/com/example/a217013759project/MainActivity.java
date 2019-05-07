  package com.example.a217013759project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import com.google.firebase.auth.FirebaseUser;

  public class MainActivity extends AppCompatActivity {
      private TextInputLayout userEmail , userPassword;
      private TextView userLogin , userRegister;
      private FirebaseUser currentUser;
      private FirebaseAuth mAuth;
      private ProgressDialog loodingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        loodingBar = new ProgressDialog(this);
        userEmail = (TextInputLayout)findViewById(R.id.registerEmail);
        userPassword = (TextInputLayout)findViewById(R.id.registerPassword);
        userLogin = (TextView) findViewById(R.id.buttonLogin);
        userRegister = (TextView) findViewById(R.id.buttonRegister);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }
      private boolean validateEmail()
      {
          String email = userEmail.getEditText().getText().toString().trim();
          if (email.isEmpty())
          {
              userEmail.setError("Enter Email");
              return false;
          }else if (email.length() > 30)
          {
              userEmail.setError("Email too long");
              return false;
          }else {
              userEmail.setError(null);
              return true;
          }
      }
      private boolean validatePassword ()
      {
          String password = userPassword.getEditText().getText().toString().trim();
          if (password.isEmpty())
          {
              userPassword.setError("Enter Password");
              return false;
          }else if (password.length() > 10)
          {
              userPassword.setError("Password too long");
              return false;
          }else {
              userPassword.setError(null);
              return true;
          }
      }
      private void AllowUserToLogin()
      {

          if (!validatePassword() | !validateEmail())
          {
            return;
          }
          else
              {
                  String email = userEmail.getEditText().getText().toString().trim();
                  String password = userPassword.getEditText().getText().toString().trim();
                  loodingBar.setTitle("sign in ");
                  loodingBar.setMessage("Please wait...!");
                  loodingBar.setCanceledOnTouchOutside(true);
                  loodingBar.show();
               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if (task.isSuccessful())
                       {
                           Intent intent = new Intent(MainActivity.this,MainUserActivity.class);
                           startActivity(intent);
                           Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                           loodingBar.dismiss();
                       }
                       else
                       {
                           String message = task.getException().toString();
                           Toast.makeText(MainActivity.this, "Error:" +message, Toast.LENGTH_SHORT).show();
                           loodingBar.dismiss();
                       }
                   }
               });
              }
      }

      @Override
      protected void onStart() {
          super.onStart();
          if (currentUser != null)
          {
              //Intent intent = new Intent(MainActivity.this,MainUserActivity.class);
             // startActivity(intent);
              Toast.makeText(this, "now you can log in", Toast.LENGTH_SHORT).show();
          }
      }
  }

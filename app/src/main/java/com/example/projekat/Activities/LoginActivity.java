package com.example.projekat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText textEmail;
    EditText textPassword;
    TextView register;
    ProgressBar progressBarL;
    TextView resetPassword;
    FirebaseAuth fAuth;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        textEmail = findViewById(R.id.textEmailL);
        textPassword = findViewById(R.id.textPasswordL);
        register = findViewById(R.id.textRegister);
        progressBarL = findViewById(R.id.progressBarL);
        resetPassword = findViewById(R.id.txtResetPassword);


        fAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference().child("User");


        //logovanje
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarL.setVisibility(View.GONE);
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    textEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    textPassword.setError("Password is required");
                    return;
                }



                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in successfuly", Toast.LENGTH_SHORT).show();
                            saveToken();
                            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Error " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ///don't have an account
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //forgot password
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    //kada se korisnik ili uredjaj uspesno prijave, Firebase stvara odgovarajuci
    // id token koji ga jednostavno identifikuje i daje pristup resursima kao sto su Realtime Firebase i storage.
    //Moze se ponovo koristiti za identifikaciju korisnika ili uredjaja
    private void saveToken(){

      FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
          @Override
          public void onComplete(@NonNull Task<String> task) {
              String token = task.getResult();
              String curr_user_id = fAuth.getCurrentUser().getUid();
              dRef.child(curr_user_id).child("device_token").setValue(token)
                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if(!task.isSuccessful()){
                                  Toast.makeText(LoginActivity.this,"Error while saving device token",Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
          }
      });
    }




}
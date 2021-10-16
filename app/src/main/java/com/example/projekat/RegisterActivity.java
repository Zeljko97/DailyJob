package com.example.projekat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import io.grpc.Context;


public class RegisterActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText confirmPassword;
    EditText phone;
    EditText profession;
    TextView loginBtn;
    ImageButton image;
    ProgressBar progressBar;
    Button btnRegister;
    TextView haveAccount;

    public static String profileImageUri = "";
    private static Bitmap photo = null;

    int TAKE_IMAGE_CODE = 10001;

    FirebaseAuth fAuth;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        confirmPassword = findViewById(R.id.textConfirmPassword);
        phone = findViewById(R.id.textPhone);
        profession = findViewById(R.id.textProfession);
        loginBtn = findViewById(R.id.textHaveAccount);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);

        btnRegister = findViewById(R.id.btnRegister);
        haveAccount = findViewById(R.id.textHaveAccount);

        fAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("User");


        //ako je korisnik ulogovan
        /*if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
            finish();
        }*/


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = name.getText().toString().trim();
                String _email = email.getText().toString().trim();
                String _password = password.getText().toString().trim();
                String _confirmPassword = confirmPassword.getText().toString().trim();
                String _phone = phone.getText().toString().trim();
                String _profession = profession.getText().toString().trim();

                if(TextUtils.isEmpty(_email)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(_password)){
                    password.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(_confirmPassword)){
                    confirmPassword.setError("Password is Required.");
                }

                if(_password.length() < 6){
                    password.setError("Password must be higer then 6.");
                }

                if(_phone.isEmpty()){
                    phone.setError("Phone is Required.");
                }


                if(photo == null)
                {
                    Toast.makeText(RegisterActivity.this,"Upload profile photo.", Toast.LENGTH_SHORT).show();
                }

                //progressBar.setVisibility(View.VISIBLE);



                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            User user = new User(
                                    _name,
                                    _email,
                                    _phone,
                                    _profession,
                                    profileImageUri);

                            FirebaseDatabase.getInstance().getReference().child("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    if(task.isSuccessful()){
                                        handleUpload();
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(RegisterActivity.this,"User created",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else{
                            Toast.makeText(RegisterActivity.this,"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


        //already have account
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }


    private void handleUpload(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //klasa implementira izlazni tok u koji se podaci upisuju, u niz bajtova

        photo.compress(Bitmap.CompressFormat.JPEG,100,baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                        Toast.makeText(RegisterActivity.this,"Profile photo updated succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"onFailure: ", e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        //setUserProfileUrl(uri);
                        String profImgUri;
                        profImgUri=uri.toString();
                        System.out.println("uri");
                        System.out.println(profImgUri);

                        DatabaseReference baza = FirebaseDatabase.getInstance().getReference();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        baza.child("User").child(userId).child("profileImageUri").setValue(profImgUri);

                        fAuth.signOut();

                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
            }
        }
    }

    public void handleImageClick(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",1);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, TAKE_IMAGE_CODE);

        }
    }
}
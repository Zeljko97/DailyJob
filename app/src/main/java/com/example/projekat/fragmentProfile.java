package com.example.projekat;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class fragmentProfile extends Fragment {

    private static final int TAKE_IMAGE_CODE = 10001;
    ImageView profileImageView;
    TextView name,profession,phoneNumber;

DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);
        name = (TextView) rootView.findViewById(R.id.txtNameProfil);
        profession = (TextView) rootView.findViewById(R.id.txtProfessionProfile);
        phoneNumber = (TextView) rootView.findViewById(R.id.txtPhoneNumberProfile);
        profileImageView = (ImageView) rootView.findViewById(R.id.imgProfile);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();//.getCurrentUser().getUid();
        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
      // DatabaseReference uidRef = databaseReference.getRoot().child("User").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String _name = snapshot.child("name").getValue(String.class);
                String _profession = snapshot.child("profession").getValue(String.class);
                String _phoneNumber = snapshot.child("phoneNumber").getValue(String.class);

                name.setText(_name);
                profession.setText(_profession);
                phoneNumber.setText(_phoneNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //return inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }




}
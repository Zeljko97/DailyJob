package com.example.projekat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projekat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDealDetailsAcitivity extends AppCompatActivity {

    TextView txtJobDealName, txtJobDealDescription,txtEmployer;
    Button openMap;

  //  DatabaseReference databaseReference,jobDealReference;
 //   FirebaseAuth firebaseAuth;
  //  String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_deal_details_acitivity);

        /*firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();//.getCurrentUser().getUid();
        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        jobDealReference = FirebaseDatabase.getInstance().getReference().child("JobDeal");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idUser = databaseReference.getKey().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


       txtJobDealName = (TextView) findViewById(R.id.txtNameJobDeal);
        txtJobDealDescription = (TextView) findViewById(R.id.txtJobDealDescription);
        txtEmployer = (TextView) findViewById(R.id.txtEmployer);

        openMap = (Button) findViewById(R.id.btnOpenMap);


            String jobDealName = getIntent().getExtras().getString("jobName");
            String description = getIntent().getExtras().getString("description");
            String employer = getIntent().getExtras().getString("employer");
            double lat = getIntent().getExtras().getDouble("latitude");
            double lon = getIntent().getExtras().getDouble("longitude");






                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nameEmployer = snapshot.child(employer).child("name").getValue(String.class);
                        txtEmployer.setText(nameEmployer);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                txtJobDealName.setText(jobDealName);
                txtJobDealDescription.setText(description);


                openMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("latitude", lat);
                        intent.putExtra("longitude", lon);
                        startActivity(intent);
                    }
                });


    }
}
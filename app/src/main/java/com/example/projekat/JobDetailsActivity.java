package com.example.projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDetailsActivity extends AppCompatActivity {

    TextView txtJobName, txtDate,txtDescription,txtCategory,txtUser;
    Button btnLocation;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);



        String id = getIntent().getExtras().getString("userId");
        String jobName = getIntent().getExtras().getString("jobName");
        String date = getIntent().getExtras().getString("date");
        String description = getIntent().getExtras().getString("description");
        String category = getIntent().getExtras().getString("category");


        txtJobName = (TextView) findViewById(R.id.txtJobName);
        txtJobName.setText(jobName);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(date);

        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(description);

        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtCategory.setText(category);

        txtUser = (TextView)findViewById(R.id.txtUserId);

        
        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("User").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String displayName = snapshot.child("name").getValue(String.class);
                txtUser.setText(displayName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lat = getIntent().getExtras().getDouble("latitude");
                double lon = getIntent().getExtras().getDouble("longitude");

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lon);
                startActivity(intent);
            }
        });


    }
}
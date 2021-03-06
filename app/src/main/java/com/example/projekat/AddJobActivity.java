package com.example.projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AddJobActivity extends AppCompatActivity {

    EditText jobName;
    Spinner category;
    EditText description;
    EditText date;

    Button btnAddLocation;
    Button btnAddJob;

    FirebaseAuth fAuth;
    DatabaseReference dRef;

    //spinner kategorije
    String[] categories = {"Struja", "Vodoinstalacije", "Popravka masina", "Servis racunara", "Ciscenje"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);



        jobName = findViewById(R.id.textJobName);
        category = findViewById(R.id.spinnerCategory);
        description = findViewById(R.id.textDescription);
        date = findViewById(R.id.textDate);

        btnAddLocation = findViewById(R.id.btnAddLocation);
        btnAddJob = findViewById(R.id.btnAddJob);

        //dodavanje spinner kategorije
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(aa);
        //


        fAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference().child("Job");

        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String _jobName = jobName.getText().toString().trim();
                String _category = category.getSelectedItem().toString().trim();
                String _description = description.getText().toString().trim();
                String _date = date.getText().toString().trim();
                String _userId = fAuth.getCurrentUser().getUid();



               if(TextUtils.isEmpty(_jobName)){
                    jobName.setError("Job name is Required");
                    return;
                }
                if(TextUtils.isEmpty(_date)){
                    date.setError("Date is Required");
                }

                Job job = new Job(
                        _jobName,
                        _category,
                        _description,
                        _date
                );

                JobData.getInstance().addNewJob(job);
                Toast.makeText(getApplicationContext(),"Added job.", Toast.LENGTH_SHORT).show();








            }
        });


    }


}
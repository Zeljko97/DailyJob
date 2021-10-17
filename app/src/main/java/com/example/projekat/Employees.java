package com.example.projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employees extends AppCompatActivity {
    Button btnApprove;
    String id;
    String jobName;
    String date;
    String description;
    String category;
    List<String> lista = new ArrayList<>();

    RequestsAdapter mainAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        String jobId = getIntent().getExtras().getString("jobId");
        id = getIntent().getExtras().getString("userId");
        jobName = getIntent().getExtras().getString("jobName");
        date = getIntent().getExtras().getString("date");
        description = getIntent().getExtras().getString("description");
        category = getIntent().getExtras().getString("category");

        btnApprove = (Button) findViewById(R.id.btnApprove);
        rv = (RecyclerView) findViewById(R.id.rvRequests);
        rv.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("RequestsJob").child(jobId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.add(snapshot.child("user").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<RequestJob> options =
                new FirebaseRecyclerOptions.Builder<RequestJob>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("RequestsJob").orderByChild("jobKey").equalTo(jobId), RequestJob.class)
                        .build();

        mainAdapter = new RequestsAdapter(options);
        rv.setAdapter(mainAdapter);

        mainAdapter.startListening();







        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


mainAdapter.startListening();
                String employee = mainAdapter.empId;

                Intent intent = new Intent(Employees.this,JobDetailsActivity.class);
                intent.putExtra("jobId",jobId);
                intent.putExtra("jobName", jobName);
                intent.putExtra("date",date);
                intent.putExtra("description",description);
                intent.putExtra("category",category);
                intent.putExtra("userId", id);
                intent.putExtra("employee",employee);
                startActivity(intent);
            }
        });
    }
}
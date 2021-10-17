package com.example.projekat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class JobDetailsActivity extends AppCompatActivity {

    TextView txtJobName, txtDate,txtDescription,txtCategory,txtUser,txtApp;
    Button btnLocation;
    Button btnApply, btnCancel, btnEmployees;
    String CurrentState ="nothing_happened";

    public static  String jobId="";
    public   String secondUserId = "";
    String[] keys;
    public  static String kreiraoPosaoId = "";
    public  static String zatrazioPosaoId = "";
     public  static String pom="";
    public  ArrayList<String> lista;

    DatabaseReference databaseReference, requestReference,jobreference;//jobReference sluzi kada prihvatimo zahtev za posao
    Query referenceForJob;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String id;
    String jobName;
    String date;
    String description;
    String category;

    String description11;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        id = getIntent().getExtras().getString("userId");
        jobName = getIntent().getExtras().getString("jobName");
         date = getIntent().getExtras().getString("date");
         description = getIntent().getExtras().getString("description");
        category = getIntent().getExtras().getString("category");

        txtJobName = (TextView) findViewById(R.id.txtJobName);
        txtJobName.setText(jobName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(date);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(description);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtCategory.setText(category);
        txtUser = (TextView)findViewById(R.id.txtUserId);
        //txtApp = (TextView)findViewById(R.id.txtApp);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(id);

        jobreference = FirebaseDatabase.getInstance().getReference().child("JobDeal");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        lista = new ArrayList<>();

        btnApply = (Button)findViewById(R.id.btnAplyForJob);
        btnCancel = (Button)findViewById(R.id.btnDeclineJob);
        btnEmployees = (Button)findViewById(R.id.btnEmployees);



        //ucitavamo Usera koji je postavio job
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



//pribavljamo job id koji se sklapa izmedju korisnika
Init();









//button za prikazivanje  lokacije
                btnLocation = (Button) findViewById(R.id.btnLocation);
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


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Decline(id);
            }
        });


    }

    private void ButtonsAction() {
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAction(id);
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Decline(id);
            }
        });
    }


    private void Init(){
        referenceForJob =  FirebaseDatabase.getInstance().getReference().child("Job").orderByChild("jobName").equalTo(jobName);
        referenceForJob.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                jobId = snapshot.getKey();
                ////////////////////////////////////


                Query dbRef;
                dbRef = FirebaseDatabase.getInstance().getReference().child("RequestsJob").orderByChild("jobKey").equalTo(jobId);

                dbRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //id request job-a
                        String requestJobId = snapshot.getKey();
                        secondUserId = snapshot.child("user").getValue(String.class);



                        requestReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("RequestsJob").child(requestJobId);
                        requestReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                LoadJobDeal();
                                ButtonsAction();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //ako jos nema zahteva u bazi
                if(secondUserId.equals("")){
                    LoadJobDeal();
                    ButtonsAction();
                }


                ////////////////////////////////////
                Toast.makeText(JobDetailsActivity.this, jobId, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }






    private void LoadJobDeal() {
        jobreference.child(jobId).child("firstUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                kreiraoPosaoId = snapshot.getValue(String.class);
                //kada nema sklopljenog posla baza vraca null, i moramo da stavimo prazan string jer u check funkciji imamo proveru da li je pribavljen ili nije
                if(kreiraoPosaoId == null)
                {
                    kreiraoPosaoId = "";
                }


                jobreference.child(jobId).child("secondUser").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        zatrazioPosaoId = snapshot.getValue(String.class);
                         CheckUserExistance(id);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Decline(String id) {

        if(CurrentState.equals("jobs"))
        {

            if(kreiraoPosaoId.equals(firebaseUser.getUid())){
                jobreference.child(jobId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CurrentState = "nothing_happened";
                        btnApply.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.GONE);
                    }
                });


            }

                        if(zatrazioPosaoId.equals(firebaseUser.getUid())) {
                            jobreference.child(jobId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    CurrentState = "nothing_happened";
                                    btnApply.setText("Apply for job");
                                    btnCancel.setVisibility(View.GONE);
                                }
                            });

                        }
        }
    }

    /////////////////////////////////////////

    private void CheckUserExistance(String id){

        if(!(kreiraoPosaoId.equals(""))){

                //da li je sklopljen posao
                if (kreiraoPosaoId.equals(firebaseUser.getUid().toString())) {
                    CurrentState = "jobs";
                    btnApply.setText("Send SMS");
                    btnCancel.setText("Cancel job");
                    btnCancel.setVisibility(View.VISIBLE);
                } //ista provera, menjamo id-je
                else {
                    if (zatrazioPosaoId.equals(firebaseUser.getUid().toString())) {

                        CurrentState = "jobs";
                        btnApply.setText("Send SMS");
                        btnCancel.setText("Cancel job");
                        btnCancel.setVisibility(View.VISIBLE);
                    }

                }
        }


        ///da li smo poslali zahtev
        if(secondUserId  != null) {
            if (!(secondUserId.equals(""))) {
                if (firebaseUser.getUid().equals(secondUserId)) {


                    requestReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String user = snapshot.child("user").getValue(String.class);
                            if(user != null) {
                                if (user.equals(firebaseUser.getUid())) {

                                    //  String status = snapshot.child("status").getValue(String.class);
                                    //  if (status.equals("pending")) {
                                    CurrentState = "I_sent_pending";
                                    btnApply.setText("Cancel Job Request");
                                    btnCancel.setVisibility(View.GONE);

                                    //  }
                                    //   if (status.equals("decline")) {
                                    //       CurrentState = "I_sent_decline";
                                    //      btnApply.setText("Cancel Job Request");
                                    //      btnCancel.setVisibility(View.GONE);

                                    //  }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //menjamo id

                    if (firebaseUser.getUid().equals(id)) {

                        btnEmployees.setVisibility(View.VISIBLE);
                        btnEmployees.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(JobDetailsActivity.this,Employees.class);
                                intent.putExtra("jobId",jobId);
                                intent.putExtra("jobName", jobName);
                                intent.putExtra("date",date);
                                intent.putExtra("description",description);
                                intent.putExtra("category",category);
                                intent.putExtra("userId", id);
                                startActivity(intent);

                            }
                        });

                        //koji zahtev ce da prihvati
                        if(getIntent().getExtras().getString("employee") != null) {

                            String user = getIntent().getExtras().getString("employee");
                            if (user != null ) {
                                secondUserId = user;
                                CurrentState = "he_sent_pending";
                                btnApply.setText("Accept Job Request");
                                btnCancel.setText("Decline job Request");
                                btnApply.setVisibility(View.VISIBLE);
                                btnCancel.setVisibility(View.VISIBLE);

                            }

                          /*  requestReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //String user = snapshot.getValue(String.class);
                                    String user = getIntent().getExtras().getString("employee");
                                    for(DataSnapshot dataSnapshot : snapshot)

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*/
                        }
                    }
                }
            }
        }
        if(CurrentState.equals("nothing_happened") && firebaseUser.getUid().equals(id)) {
            btnApply.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }



    }






    ////////////////////////////////////////




    private void PerformAction(String id){


        //ako nije poslat nikakav zahtev
        if(CurrentState.equals("nothing_happened")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");

            String newId = jobId;
            RequestJob requestJob = new RequestJob(jobId,firebaseUser.getUid());
            RequestJobData.getInstance().addNewRequest(requestJob);
            btnCancel.setVisibility(View.GONE);
            btnApply.setText("Cancel Job Request");
            CurrentState = "I_sent_pending";

          Init();
         //   requestReference.child("status").setValue("pending");
            return;

        }

        //ako je zahtev poslat, sad moze samo da se izbrise

        if(CurrentState.equals("I_sent_pending") || CurrentState.equals("I_sent_decline")) {
            requestReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    CurrentState = "nothing_happened";
                    //posto smo izbrisali zahtev, sad ponovo mozemo da posaljemo
                    btnApply.setText("Send Job Request");
                    btnCancel.setVisibility(View.GONE);
                }
            });
        }

        //ako nam je stigao zahtev, prihvatamo ili odbijamo

        if(CurrentState.equals("he_sent_pending")){

            if(firebaseUser.getUid().equals(id)){
                String nameJob = txtJobName.getText().toString();

                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("Job").child(jobId);
                dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double latitude = snapshot.child("latitude").getValue(Double.class);
                        double longitude = snapshot.child("longitude").getValue(Double.class);
                        description11 = snapshot.child("description").getValue(String.class);
                        Toast.makeText(JobDetailsActivity.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();

                        //dodavanje job deal - a
                        JobDeal jobDeal = new JobDeal(id, secondUserId, nameJob, latitude, longitude, description11);
                        JobDealData.getInstance().addNewJobDeal(jobDeal, jobId);

                        CurrentState = "jobs";
                        btnApply.setText("Send SMS");
                        btnCancel.setText("Cancel job");
                        btnCancel.setVisibility(View.VISIBLE);


                        //requestReference.removeValue();

                        Query req = FirebaseDatabase.getInstance().getReference().child("RequestsJob").orderByChild("jobKey").equalTo(getIntent().getExtras().getString("jobId"));
                        req.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String reqId = snapshot.getKey();

                                DatabaseReference reqRef = FirebaseDatabase.getInstance().getReference().child("RequestsJob").child(reqId);
                                reqRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        reqRef.removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //brisemo i ostale zahteve za ovaj posao kada smo jedan prihvatili


                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Job").child(jobId);
     /*   Toast.makeText(JobDetailsActivity.this,nameJob,Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,String.valueOf(latit),Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,String.valueOf(longit),Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,desc,Toast.LENGTH_SHORT).show();*/
                        dbref.removeValue();

                        Intent intent = new Intent(JobDetailsActivity.this,JobDealDetailsAcitivity.class);
                        intent.putExtra("jobName",nameJob);
                        intent.putExtra("employer",id);
                        intent.putExtra("description", description11);
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        startActivity(intent);

                        showJobDealDetails(nameJob, latitude, longitude, description11);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


           /* requestReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String user = snapshot.child("user").getValue(String.class);
                    if(firebaseUser.getUid().equals(id)){
                        String userId = secondUserId;
                        requestReference.removeValue();
                                String nameJob = txtJobName.getText().toString();
                                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("Job").child(jobId);
                                dRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        double latitude = snapshot.child("latitude").getValue(Double.class);
                                        double longitude = snapshot.child("longitude").getValue(Double.class);
                                        description11 = snapshot.child("description").getValue(String.class);
                                        Toast.makeText(JobDetailsActivity.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();

                                        //dodavanje job deal - a
                                        JobDeal jobDeal = new JobDeal(id, userId, nameJob, latitude, longitude, description11);
                                        JobDealData.getInstance().addNewJobDeal(jobDeal, jobId);

                                        CurrentState = "jobs";
                                        btnApply.setText("Send SMS");
                                        btnCancel.setText("Cancel job");
                                        btnCancel.setVisibility(View.VISIBLE);


                                        showJobDealDetails(nameJob, latitude, longitude, description11);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/


        }



    }


    //kada je posao prihvacen
    private void showJobDealDetails(String nameJob,double latit, double longit, String desc){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Job").child(jobId);
     /*   Toast.makeText(JobDetailsActivity.this,nameJob,Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,String.valueOf(latit),Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,String.valueOf(longit),Toast.LENGTH_SHORT).show();
        Toast.makeText(JobDetailsActivity.this,desc,Toast.LENGTH_SHORT).show();*/
        dbref.removeValue();

        Intent intent = new Intent(JobDetailsActivity.this,JobDealDetailsAcitivity.class);
        intent.putExtra("jobName",nameJob);
        intent.putExtra("employer",id);
        intent.putExtra("description", desc);
        intent.putExtra("latitude",latit);
        intent.putExtra("longitude",longit);
        startActivity(intent);
    }


}
package com.example.projekat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projekat.Classes.JobDeal;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class JobsFragment extends Fragment {

    private RecyclerView rv;
    private DatabaseReference databaseReference;
    JobDealAdapter mainAdapter;


    DatabaseReference ref,jobDealReference;
       FirebaseAuth firebaseAuth;
      String idUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_jobs,container,false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        //trenutno prijavljeni user
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        idUser = user.getUid();




        jobDealReference = FirebaseDatabase.getInstance().getReference().child("JobDeal");

        LoadFromDatabase(idUser);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void LoadFromDatabase(String id){

        //ucitavaju se poslovi za koje je user aplicirao i dobio
        Toast.makeText(getContext(),id, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<JobDeal> options =
                new FirebaseRecyclerOptions.Builder<JobDeal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("JobDeal").orderByChild("secondUser").equalTo(id), JobDeal.class)
                        .build();



        mainAdapter = new JobDealAdapter(options);
        rv.setAdapter(mainAdapter);

        mainAdapter.startListening();

    }
}
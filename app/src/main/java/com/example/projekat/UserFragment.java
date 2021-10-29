package com.example.projekat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekat.Classes.UsersModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserFragment extends Fragment {

    private RecyclerView rv;
    private DatabaseReference databaseReference;
    UserAdapter usersAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user,container,false);
        rv = (RecyclerView) rootView.findViewById(R.id.recyclerViewUser);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        LoadFromDatabase();


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user, container, false);
        return rootView;
    }


    private void LoadFromDatabase(){


        FirebaseRecyclerOptions<UsersModel> options =
                new FirebaseRecyclerOptions.Builder<UsersModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User"), UsersModel.class)
                        .build();

        usersAdapter = new UserAdapter(options);
        rv.setAdapter(usersAdapter);

        usersAdapter.startListening();

    }
}
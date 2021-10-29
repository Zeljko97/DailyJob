package com.example.projekat.Classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class JobDealData {

    private ArrayList<JobDeal> jobsDeal;
    private HashMap<String, Integer> jobsDealKeyIndexMapping;
    private DatabaseReference database;
    private static final String FIREBASE_CHILD = "JobDeal";

    private JobDealData(){
        jobsDeal = new ArrayList<>();

        jobsDealKeyIndexMapping = new HashMap<String,Integer>();
        database = FirebaseDatabase.getInstance().getReference();
        database.child(FIREBASE_CHILD).addChildEventListener(childEventListener);
        database.child(FIREBASE_CHILD).addListenerForSingleValueEvent(parentEventListener);

    }

    ValueEventListener parentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };



    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
    };

    public void addNewJobDeal(JobDeal jobDeal,String key){
        //String key = database.push().getKey();

        jobsDeal.add(jobDeal);
        jobsDealKeyIndexMapping.put(key,jobsDeal.size() - 1);
        database.child(FIREBASE_CHILD).child(key).setValue(jobDeal);

    }

    public void deleteJobDeal(int index){
        database.child(FIREBASE_CHILD).child(jobsDeal.get(index).key).removeValue();
        jobsDeal.remove(index);

        recreateKeyIndexMappind();
    }

    private void recreateKeyIndexMappind() {

        jobsDealKeyIndexMapping.clear();
        for(int i = 0;i<jobsDeal.size();i++){
            jobsDealKeyIndexMapping.put(jobsDeal.get(i).key,i);
        }
    }



    private static class SingletonHolder{
        public static final JobDealData instance = new JobDealData();


    }

    public static JobDealData getInstance(){
        return SingletonHolder.instance;
    }
    public ArrayList<JobDeal> getJobsDeal(){
        return jobsDeal;
    }
    public JobDeal getJobDeal(int index){
        return jobsDeal.get(index);
    }
}

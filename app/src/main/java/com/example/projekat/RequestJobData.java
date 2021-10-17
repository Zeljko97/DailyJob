package com.example.projekat;

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

public class RequestJobData {

    private ArrayList<RequestJob> requests;
    private HashMap<String,Integer> requestsKeyIndexMapping;
    private DatabaseReference database;
    private static final String FIREBASE_CHILD = "RequestsJob";

    private RequestJobData(){
        requests = new ArrayList<>();

        requestsKeyIndexMapping = new HashMap<String,Integer>();
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


    public void addNewRequest(RequestJob requestJob){
        String key = database.push().getKey();

        requests.add(requestJob);
        requestsKeyIndexMapping.put(key,requests.size()-1);
        database.child(FIREBASE_CHILD).child(key).setValue(requestJob);
        requestJob.key = key;
    }
    public void deleteRequest(int index){
        database.child(FIREBASE_CHILD).child(requests.get(index).key).removeValue();
        requests.remove(index);
        recreateKeyIndexMapping();
    }

    private void recreateKeyIndexMapping() {

        requestsKeyIndexMapping.clear();
        for(int i = 0;i<requests.size();i++){
            requestsKeyIndexMapping.put(requests.get(i).key,i);
        }
    }


    private static class SingletonHolder{
        public static final RequestJobData instance = new RequestJobData();
    }

    public static RequestJobData getInstance(){
        return SingletonHolder.instance;
    }
    public ArrayList<RequestJob> getRequests(){
        return requests;
    }
    public RequestJob getRequestJob(int index){
        return requests.get(index);
    }

}

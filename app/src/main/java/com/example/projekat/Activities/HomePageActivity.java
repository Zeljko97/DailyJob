package com.example.projekat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.example.projekat.Classes.MainModel;
import com.example.projekat.MainAdapter;
import com.example.projekat.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,PopupMenu.OnMenuItemClickListener{

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,dRef;
    private Spinner spinner;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String idUser;
    MainAdapter mainAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       spinner = (Spinner)findViewById(R.id.spinner);
       spinner.setOnItemSelectedListener(this);

       databaseReference = FirebaseDatabase.getInstance().getReference().child("Job");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        idUser = user.getUid();



    }

    @Override
    protected void onStart() {
        super.onStart();
        getCategories();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void getCategories(){
        final List<String> list = new ArrayList<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    String category = snapshot1.child("category").getValue(String.class);
                    if(!list.contains(category))
                        list.add(category);
                    ArrayAdapter<String> a = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,list);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(a);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void LoadFromDatabase(String text){
                FirebaseRecyclerOptions<MainModel> options =
                        new FirebaseRecyclerOptions.Builder<MainModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Job").orderByChild("category").equalTo(text), MainModel.class)
                                .build();

                 mainAdapter1 = new MainAdapter(options);
                recyclerView.setAdapter(mainAdapter1);

                mainAdapter1.startListening();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       String text = spinner.getSelectedItem().toString();
        LoadFromDatabase(text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_manu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.itemAddJob:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                return true;
            case R.id.itemLogout:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            case R.id.itemProfile:
                startActivity(new Intent(getApplicationContext(), AllUsersActivity.class));
                return true;
            default:
                return false;
        }
    }
}
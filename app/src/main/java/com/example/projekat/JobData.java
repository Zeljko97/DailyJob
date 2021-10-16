package com.example.projekat;

import android.media.MediaParser;

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

public class JobData {

    private ArrayList<Job> jobs;
    private HashMap<String, Integer>jobsKeyIndexMapping;
    private DatabaseReference database;
    private static final String FIREBASE_CHILD = "Job";

    private JobData(){
        jobs = new ArrayList<>();
        //da bi bila dodata podrska za rad sa Firebase bazom, potrebno je mapiranje razlicitih domena kljuceva.
        //Firebasse baza svakom objektu dodeljuje kljuc tipa String, koja zavisi od vremena dodavanja objekta.
        //Ovo mapiranje je potrebno kako bi moglo da se pristupi odgovarajucem elementu na osnovu indeksa u slucaju kada je poznat samo kljuc
        jobsKeyIndexMapping = new HashMap<String, Integer>();
        database = FirebaseDatabase.getInstance().getReference();
        database.child(FIREBASE_CHILD).addChildEventListener(childEventListener);
        database.child(FIREBASE_CHILD).addListenerForSingleValueEvent(parentEventListener);

    }

    //da bi aplikacija pratila promene nad podacima, potrebno je registrovati event listener,
    //koji se aktiviraju prilikom inicijalnog ucitavanja iz baze podataka kao i prilikom promena koje su
    //izvrsene nad podacima
    //Posto je osnovni model podataka aplikacije listam u tom cilju je potrebno dodati ChildEventListener i SinglevalueEventListener referenci baze podataka
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

    public void addNewJob(Job job){
        String key = database.push().getKey();
        //Prvo se od od Firebase baze podataka pribavi kljuc, koji ce biti dodeljen elementu
        //postaviti odgovarajuci atribut key i dodati par kljuc-indeks u HashMap a zatim dodati objekat u bazu

        jobs.add(job);
        jobsKeyIndexMapping.put(key,jobs.size()-1);
        database.child(FIREBASE_CHILD).child(key).setValue(job);
        job.key = key;
    }

    public void deleteJob(int index){
        database.child(FIREBASE_CHILD).child(jobs.get(index).key).removeValue();
        jobs.remove(index);
        //Svaki put kada je element izbacen iz liste, potrebno je rekreirati mapiranje kljuceva
        recreateKeyIndexMapping();
    }
    private void recreateKeyIndexMapping(){
        jobsKeyIndexMapping.clear();
        for(int i = 0;i<jobs.size();i++)
        {
            jobsKeyIndexMapping.put(jobs.get(i).key,i);
        }
    }


    //Singleton patern (obezbedjuje da odredjena klasa ima aktivan maks. jedam objekat
    //Posto nam je potrebno da ovoj klasi pristupamo iz drugih klasa, a zelimo da se kljucni podaci nalaze na jednom mestu
    //implementiramo ovu klasu prema Singleton paternu, vodeci racuna da implementirana klasa bude Thread Safe Singleton
    private  static class SingletonHolder{

        public static final JobData instance = new JobData();

    }
    //definisemo operaciju instance koja obezbedjuje korisniku pristup jedinom kreiranom objektu
    public static JobData getInstance(){
        return SingletonHolder.instance;
    }
    public ArrayList<Job> getJobs(){
        return jobs;
    }
    public Job getJob(int index){
        return jobs.get(index);
    }
}

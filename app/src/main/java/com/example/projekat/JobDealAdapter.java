package com.example.projekat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.Activities.JobDealDetailsAcitivity;
import com.example.projekat.Classes.JobDeal;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class JobDealAdapter extends FirebaseRecyclerAdapter<JobDeal,JobDealAdapter.jobDealViewHolder> {

    public JobDealAdapter(@NonNull FirebaseRecyclerOptions<JobDeal> options){
        super(options);
    }
    //  @Override
    public void onBindViewHolder(JobDealAdapter.jobDealViewHolder viewHolder, int position, JobDeal model) {
        viewHolder.jobName.setText(model.getJobName());
        viewHolder.employer.setText(model.getFirstUser());
        viewHolder.description = model.getDescription();
        viewHolder.latitude = model.getLatitude();
        viewHolder.longitude = model.getLongitude();
        viewHolder.idUser = model.getSecondUser();


    }

    @Override
    public JobDealAdapter.jobDealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobdealitem,parent,false);
        return new JobDealAdapter.jobDealViewHolder(view, view.getContext());

    }

    class jobDealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView jobName,employer;
        Context context;
        String description;
        String idUser;
        double latitude;
        double longitude;


        public jobDealViewHolder(@NonNull View itemView, Context context) {
            super(itemView);


            jobName = (TextView) itemView.findViewById(R.id.jobDealName);
            employer = (TextView) itemView.findViewById(R.id.jobDealEmployer);

            this.context = context;


            itemView.setOnClickListener(this);
            itemView.setClickable(true);




        }


        @Override
        public void onClick(View view) {


            Intent intent = new Intent(context, JobDealDetailsAcitivity.class);

            intent.putExtra("jobName", this.jobName.getText());
            intent.putExtra("employer",this.employer.getText());
            intent.putExtra("latitude",this.latitude);
            intent.putExtra("longitude",this.longitude);
            intent.putExtra("description",this.description);
            intent.putExtra("employee",this.idUser);

            context.startActivity(intent);




        }
    }
}

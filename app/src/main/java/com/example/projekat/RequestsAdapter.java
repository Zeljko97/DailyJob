package com.example.projekat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestsAdapter extends FirebaseRecyclerAdapter<RequestJob,RequestsAdapter.myViewHolder> {


    String empId = "";
    public RequestsAdapter(@NonNull FirebaseRecyclerOptions<RequestJob> options){
        super(options);
    }
    //  @Override
    public void onBindViewHolder(RequestsAdapter.myViewHolder viewHolder, int position, RequestJob model) {

        viewHolder.employeeId = model.getUser();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Job").child(model.getJobKey());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String jobN = snapshot.child("jobName").getValue(String.class);
                viewHolder.jobName.setText(jobN);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User").child(model.getUser());
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                viewHolder.employee.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        /*Glide.with(viewHolder.img.getContext())
        .load(model.getSurl())
                .placeHolder(R.drawable.common_google_signin_btn_icon_dark)
                .cirlceCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(viewHolder.img);
*/
    }

    @Override
    public RequestsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent,false);
        return new RequestsAdapter.myViewHolder(view, view.getContext());

    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView jobName,employee ;
        String employeeId;
        Context context;



        public myViewHolder(@NonNull View itemView,Context context) {
            super(itemView);


            jobName = (TextView) itemView.findViewById(R.id.requestJobName);
            employee = (TextView) itemView.findViewById(R.id.employeeName);



            itemView.setOnClickListener(this);
            itemView.setClickable(true);




        }


        @Override
        public void onClick(View view) {

            empId = employeeId;


         /*   Intent intent = new Intent(context, JobDetailsActivity.class);

            intent.putExtra("userId",this.userId);
            intent.putExtra("jobName", this.jobName.getText());
            intent.putExtra("date",this.date.getText());
            intent.putExtra("category",this.category.getText());
            intent.putExtra("description",this.description.getText());
            intent.putExtra("latitude",this.latitude);
            intent.putExtra("longitude",this.longitude);

            context.startActivity(intent);*/




        }
    }
}

package com.example.projekat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {


    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options){
        super(options);
    }
                       //  @Override
    public void onBindViewHolder(myViewHolder viewHolder, int position, MainModel model) {
        viewHolder.jobName.setText(model.getJobName());
        viewHolder.date.setText(model.getDate());
        viewHolder.category.setText(model.getCategory());
        viewHolder.description.setText(model.getDescription());
        viewHolder.userId = model.getKey();
        viewHolder.latitude = model.getLatitude();
        viewHolder.longitude = model.getLongitude();





        /*Glide.with(viewHolder.img.getContext())
        .load(model.getSurl())
                .placeHolder(R.drawable.common_google_signin_btn_icon_dark)
                .cirlceCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(viewHolder.img);
*/
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view, view.getContext());

    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView img;
        TextView jobName,date,category,description;
        Context context;
        String userId;
        double latitude;
        double longitude;


        public myViewHolder(@NonNull View itemView,Context context) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            jobName = (TextView) itemView.findViewById(R.id.jobName);
            date = (TextView) itemView.findViewById(R.id.date);
            category = (TextView) itemView.findViewById(R.id.category);
            description = (TextView) itemView.findViewById(R.id.description);
            this.context = context;
            userId = "";
            latitude = 0;
            longitude = 0;

            itemView.setOnClickListener(this);
            itemView.setClickable(true);




        }


        @Override
        public void onClick(View view) {


            Intent intent = new Intent(context, JobDetailsActivity.class);

            intent.putExtra("userId",this.userId);
            intent.putExtra("jobName", this.jobName.getText());
            intent.putExtra("date",this.date.getText());
            intent.putExtra("category",this.category.getText());
            intent.putExtra("description",this.description.getText());
            intent.putExtra("latitude",this.latitude);
            intent.putExtra("longitude",this.longitude);

            context.startActivity(intent);




        }
    }

}

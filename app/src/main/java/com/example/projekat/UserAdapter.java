package com.example.projekat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<UsersModel,UserAdapter.vHolder> {

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UsersModel> options){
        super(options);
    }




    public void onBindViewHolder(vHolder viewHolder, int position, UsersModel model){
        viewHolder.name.setText(model.getName());
        viewHolder.phoneNumber.setText(model.getPhoneNumber());
        viewHolder.email.setText(model.getEmail());
        viewHolder.profession.setText(model.getProfession());

        String name = model.getName();
      String img = model.getProfileImageUri();

      viewHolder.setImg(img);
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new vHolder(view,view.getContext());
    }




    class vHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView img;
        TextView name,profession,email,phoneNumber;
        Context context;

        public vHolder(@NonNull View itemView, Context context){
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.img2);
            name = (TextView) itemView.findViewById(R.id.txtUserName);
            profession = (TextView) itemView.findViewById(R.id.txtProfession);
            email = (TextView) itemView.findViewById(R.id.txtEmail);
            phoneNumber = (TextView) itemView.findViewById(R.id.txtPhoneNumber);
            this.context = context;

            itemView.setOnClickListener(this);
            itemView.setClickable(true);


        }

        @Override
        public void onClick(View view) {

        }

        public void setImg(String pom){
            Uri myUri = Uri.parse(pom);
            Picasso.get().load(myUri).into(img);
        }
    }

}






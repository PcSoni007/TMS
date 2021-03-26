package com.prakhar.tms.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.tms.R;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {
    private Context mc;
    private List<BiddingVehicle> biddingVehicles;
    private BiddingVehicle vehicle;
    private OnItemClickListner onItemClickListner;
    private View view;

//    private Object OnItemClickListner;


    public UserAdapter(Context mc, List<BiddingVehicle> userlists, OnItemClickListner onItemClickListner ) {
        this.mc = mc;
        this.biddingVehicles = userlists;
        this.onItemClickListner=onItemClickListner;
//        Log.e("UserAdapter", userlists.get(0).getBiddingTitle()+"");
//        Log.e("UserAdapter", userlists.get(1).getOwnerloc().toString());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_demo,parent,false);
//        UserAdapter.UsersViewHolder V = new UserAdapter.UsersViewHolder(view);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return new UsersViewHolder(view,onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        vehicle = biddingVehicles.get(position);
//        holder.txt1.setText(userlist.getUserId());
        String PId= String.valueOf(vehicle.getBiddingTitle());
        String PName = String.valueOf(vehicle.getBiddingPrice());
        String PPrice = String.valueOf(vehicle.getVehicleName());
        String PQuant = String.valueOf(vehicle.getVehicleModel());
        holder.txt1.setText(PId);
        holder.txt2.setText(PName);
        holder.txt3.setText(PPrice);
        holder.txt4.setText(PQuant);
//        Picasso.with(mc).load(vehicle.getImg()).into(holder.img);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClick(userlist.getId());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return biddingVehicles.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        OnItemClickListner onItemClickListner;
        private TextView txt1,txt2,txt3,txt4;
        ImageView img;
        public UsersViewHolder(@NonNull View itemView, OnItemClickListner onItemClickListner) {
            super(itemView);
            txt1= (TextView) itemView.findViewById(R.id.itemTitle);
            txt2= (TextView) itemView.findViewById(R.id.itemJpay);
            txt3= (TextView) itemView.findViewById(R.id.itemSource);
            txt4= (TextView) itemView.findViewById(R.id.itemDestination);
//            img = (ImageView) itemView.findViewById(R.id.img);
            this.onItemClickListner= onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListner.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemClickListner {
        void onItemClick(int id);

    }

}



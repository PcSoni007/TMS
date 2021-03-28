package com.prakhar.tms.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.fragments.BiddingFragment;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.prakhar.tms.ui.BIdVehicleDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class BidVehicleAdapter extends RecyclerView.Adapter<BidVehicleAdapter.UsersViewHolder>{
    private Context mc;
    private List<BiddingVehicle> biddingVehicles;
    private BiddingVehicle vehicle;
    private OnItemClickListner onItemClickListner;
    private View view;
    List<StorageReference> ImgList;
//    private Object OnItemClickListner;


    public BidVehicleAdapter(Context mc, List<BiddingVehicle> userlists, OnItemClickListner onItemClickListner, List<StorageReference> storageReferencesList) {
        this.mc = mc;
        this.biddingVehicles = userlists;
        this.onItemClickListner=onItemClickListner;
        this.ImgList = storageReferencesList;
        Log.e("UserAdapter", userlists.get(0).getBiddingTitle()+"");
//        Log.e("UserAdapter", userlists.get(1).getOwnerloc().toString());
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line_light,parent,false);
//        UserAdapter.UsersViewHolder V = new UserAdapter.UsersViewHolder(view);

        return new UsersViewHolder(view,onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        vehicle = biddingVehicles.get(position);
//        holder.txt1.setText(userlist.getUserId());
        String Price= String.valueOf(vehicle.getBiddingPrice());
        String Description = String.valueOf(vehicle.getBiddingDes());
        String Model = String.valueOf(vehicle.getVehicleModel());
        String city = String.valueOf(vehicle.getOwnerloc());
        holder.txt1.setText(Price);
        holder.txt2.setText(Description);
        holder.txt3.setText(Model);
        holder.txt4.setText(city);
        final String[] ImgUrl = new String[1];
        ImgList.get(position).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.img);
                ImgUrl[0] = uri.toString();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mc, "Item "+position+" Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mc , BIdVehicleDetailActivity.class);

                intent.putExtra("BiddingTitle",biddingVehicles.get(position).getBiddingTitle().toString());
                intent.putExtra("BiddingStart",biddingVehicles.get(position).getBiddingPrice().toString());
                intent.putExtra("BiddingDescription",biddingVehicles.get(position).getBiddingDes().toString());
                intent.putExtra("VehicleNo",biddingVehicles.get(position).getVehicleNo().toString());
                intent.putExtra("VehicleType",biddingVehicles.get(position).getVehicleType().toString());
                intent.putExtra("VehicleName",biddingVehicles.get(position).getVehicleName().toString());
                intent.putExtra("VehicleModel",biddingVehicles.get(position).getVehicleModel().toString());
                intent.putExtra("VehicleTire",biddingVehicles.get(position).getNoOfTires().toString());
                intent.putExtra("VehicleLoadingCap",biddingVehicles.get(position).getLoadingCap().toString());
                intent.putExtra("VehicleAverage",biddingVehicles.get(position).getAverage().toString());
                intent.putExtra("VehicleAvailability",biddingVehicles.get(position).getKmDriven().toString());
                intent.putExtra("OwnerName",biddingVehicles.get(position).getVehicleOwner().toString());
                intent.putExtra("OwnerLocation",biddingVehicles.get(position).getOwnerloc().toString());
                intent.putExtra("OwnerContact",biddingVehicles.get(position).getOwnerCont().toString());
                intent.putExtra("VehicleImg",ImgUrl[0]);

                mc.startActivity(intent);

            }
        });
//        holder.txt3.setText(PPrice);
//        holder.txt4.setText(PQuant);
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
            txt1= (TextView) itemView.findViewById(R.id.price);
            txt2= (TextView) itemView.findViewById(R.id.brief);
            txt3= (TextView) itemView.findViewById(R.id.model);
            txt4= (TextView) itemView.findViewById(R.id.location);
            img = (ImageView) itemView.findViewById(R.id.image);
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

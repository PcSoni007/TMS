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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.fragments.BiddingFragment;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.VehicleDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyVehicleAdapter extends RecyclerView.Adapter<MyVehicleAdapter.UsersViewHolder>{
    private Context mc;
    private List<VehicleDetails> myVehicelList;
    private BiddingVehicle vehicle;
    private View view;
    List<StorageReference> ImgList;
    private OnItemClickListner onItemClickListner;



    public MyVehicleAdapter(Context mc, List<VehicleDetails> userlists, OnItemClickListner onItemClickListner, List<StorageReference> storageReferencesList) {
        this.mc = mc;
        this.myVehicelList = userlists;
        this.onItemClickListner=onItemClickListner;
        this.ImgList = storageReferencesList;
        Log.e("UserAdapter", userlists.get(0).getVehicleName()+"");
//        Log.e("UserAdapter", userlists.get(1).getOwnerloc().toString());
    }



    @NonNull
    @Override
    public MyVehicleAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line_light,parent,false);
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
//        holder.txt1.setText(userlist.getUserId());
        String PId= String.valueOf(vehicle.getBiddingTitle());
        String PName = String.valueOf(vehicle.getBiddingPrice());
        String PPrice = String.valueOf(vehicle.getVehicleName());
        String PQuant = String.valueOf(vehicle.getVehicleModel());
        holder.txt1.setText(PId);
        holder.txt2.setText(PName);

        ImgList.get(position).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.img);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myVehicelList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        OnItemClickListner onItemClickListner;
        private TextView txt1,txt2,txt3,txt4;
        ImageView img;
        public UsersViewHolder(@NonNull View itemView, OnItemClickListner onItemClickListner) {
            super(itemView);
            txt1= (TextView) itemView.findViewById(R.id.name);
            txt2= (TextView) itemView.findViewById(R.id.brief);
//            txt3= (TextView) itemView.findViewById(R.id.itemSource);
//            txt4= (TextView) itemView.findViewById(R.id.itemDestination);
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

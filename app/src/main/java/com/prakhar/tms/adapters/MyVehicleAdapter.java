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
    private View view;
    List<StorageReference> ImgList;
    private OnItemClickListner onItemClickListner;
    private String CurrentUser;


    public MyVehicleAdapter(Context mc, List<VehicleDetails> userlists, OnItemClickListner onItemClickListner, List<StorageReference> storageReferencesList, String mUser) {
        this.mc = mc;
        this.myVehicelList = userlists;
        this.onItemClickListner=onItemClickListner;
        this.ImgList = storageReferencesList;
        this.CurrentUser = mUser;
        Log.e("MyVehicleAdapter", userlists.get(0).getVehicleName()+"");
//        Log.e("UserAdapter", userlists.get(1).getOwnerloc().toString());
    }



    @NonNull
    @Override
    public MyVehicleAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_vehicle_item,parent,false);
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


        String Vname= String.valueOf(myVehicelList.get(position).getVehicleName());
        String Vmodel = String.valueOf(myVehicelList.get(position).getVehicleModel());
        String Vno = String.valueOf(myVehicelList.get(position).getVehicleNo());
        String Vtype = String.valueOf(myVehicelList.get(position).getVehicleType());
        String Vload = String.valueOf(myVehicelList.get(position).getLoadingCap());
        holder.txt1.setText(Vname);
        holder.txt2.setText(Vmodel);
        holder.txt3.setText(Vno);
        holder.txt4.setText(Vtype);
        holder.txt5.setText(Vload);

//        if (myVehicelList.get(position).getVehicleNo())
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
        private TextView txt1,txt2,txt3,txt4,txt5;
        ImageView img;
        public UsersViewHolder(@NonNull View itemView, OnItemClickListner onItemClickListner) {
            super(itemView);
            txt1= (TextView) itemView.findViewById(R.id.itemTitle);
            txt2= (TextView) itemView.findViewById(R.id.itemJobDate);
            txt3= (TextView) itemView.findViewById(R.id.itemJpay);
            txt4= (TextView) itemView.findViewById(R.id.itemSource);
            txt5= (TextView) itemView.findViewById(R.id.itemDestination);
            img = (ImageView) itemView.findViewById(R.id.cardImg);
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

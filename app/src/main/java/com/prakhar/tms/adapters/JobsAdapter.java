package com.prakhar.tms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakhar.tms.R;
import com.prakhar.tms.modals.Jobs;
import com.prakhar.tms.modals.Jobs;

import java.util.ArrayList;
import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.UsersViewHolder>{
    private Context mc;
    private List<Jobs> JobList;
    private Jobs job;
    private OnItemClickListner onItemClickListner;
    private View view;

//    private Object OnItemClickListner;


    public JobsAdapter(Context mc, List<Jobs> userlists, OnItemClickListner onItemClickListner ) {
        this.mc = mc;
        this.JobList = userlists;
        this.onItemClickListner=onItemClickListner;
        Log.e("JobsAdapter", userlists.get(0).getJobTitle()+"");
//        Log.e("UserAdapter", userlists.get(1).getOwnerloc().toString());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false);
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
        job = JobList.get(position);
//        holder.txt1.setText(userlist.getUserId());
        String JTitle= String.valueOf(job.getJobTitle());
        String JDate = String.valueOf(job.getJobDate());
        String JPay = String.valueOf(job.getJobPay());
        String JSource = String.valueOf(job.getSource());
        String JDestination = String.valueOf(job.getDestination());
        holder.txt1.append(JTitle);
        holder.txt2.append(JDate);
        holder.txt3.append(JPay);
        holder.txt4.append(JSource);
        holder.txt5.append(JDestination);

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
        return JobList.size();
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

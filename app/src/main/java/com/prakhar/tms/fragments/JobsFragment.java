package com.prakhar.tms.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prakhar.tms.R;
import com.prakhar.tms.adapters.BidVehicleAdapter;
import com.prakhar.tms.adapters.JobsAdapter;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.prakhar.tms.utils.Tools;
import com.prakhar.tms.widgets.SpacingItemDecoration;

import java.util.ArrayList;

public class JobsFragment extends Fragment implements JobsAdapter.OnItemClickListner {

    private View view;
    private String TAG = "JobsFragment";
    private ListView JobList;
    private ArrayList<Jobs> list = new ArrayList<>();
    JobsAdapter adapter;
    DatabaseReference myRef;


    TextView JobTitle;
    TextView JobDate;
    TextView JobPay;
    TextView JobSource;
    TextView JobDestination;
    DatabaseReference reference;
    RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<Jobs>();
        myRef = FirebaseDatabase.getInstance().getReference().child("job_details");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jobs, container, false);

        LinearLayout lyt_progress = (LinearLayout) view.findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
        initComponent();
        recyclerView.setVisibility(View.GONE);


//        Log.e(TAG, list.get(0).getJobTitle()+"");
//        Log.e(TAG, list.get(2).getJobTitle().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BiddingVehicle bid = snapshot.getValue(BiddingVehicle.class);
                if (bid != null) {
                    Log.e("JobFragment", snapshot.getValue().toString());
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        // TODO: handle the post
                        list.add(postSnapshot.getValue(Jobs.class));
                        Log.e("JobFragment", postSnapshot.getValue().toString());
                        Log.e("JobFragment", postSnapshot.getValue(Jobs.class).getJobTitle().toString());
                    }
//                    Log.e("BidVehicleList", bid.getBiddingDes().toString());
                    adapter = new JobsAdapter(getContext(), list, JobsFragment.this);
                    lyt_progress.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);

                }
                else {
                    Log.e("JobFragment", "Data not availble");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("JobFragment", "You are not permitted to access this data");
            }
        });




        return view;
    }
    private void initComponent() {

        JobTitle = view.findViewById(R.id.itemTitle);
        JobDate = view.findViewById(R.id.itemJobDate);
        JobPay = view.findViewById(R.id.itemJpay);
        JobSource = view.findViewById(R.id.itemSource);
        JobDestination = view.findViewById(R.id.itemDestination);

        recyclerView = (RecyclerView) view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(int id) {

    }
}
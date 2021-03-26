package com.prakhar.tms.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.prakhar.tms.R;
import com.prakhar.tms.modals.Jobs;

public class AddBidFragment extends Fragment {

    private View view;
    private CardView AddBid;
    private CardView AddVehicle;
    private CardView AddJobs;

    public AddBidFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_bid, container, false);
        init();


        return view;
    }

    public void init(){

        AddBid = view.findViewById(R.id.addBid);
        AddVehicle = view.findViewById(R.id.addVehicle);
        AddJobs = view.findViewById(R.id.addJob);

        AddBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBidVehicleFragment addBidVehicleFragment = new AddBidVehicleFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, addBidVehicleFragment,null);
                fragmentTransaction.commit();
            }
        });

        AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VehiclesFragment vehiclesFragment = new VehiclesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, vehiclesFragment,null);
                fragmentTransaction.commit();
            }
        });

        AddJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddJobFragment addJobFragment = new AddJobFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, addJobFragment,null);
                fragmentTransaction.commit();
            }
        });

    }
}
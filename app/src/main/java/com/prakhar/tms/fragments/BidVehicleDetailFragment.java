package com.prakhar.tms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prakhar.tms.R;
import com.prakhar.tms.modals.BiddingVehicle;

class BidVehicleDetailFragment extends Fragment {

    BiddingVehicle biddingVehicle;


    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bid_vehicle_detail, container, false);
        return view;
    }


}

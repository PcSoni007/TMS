package com.prakhar.tms.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.Jobs;

public class AddBidFragment extends Fragment {

    private View view;
    private CardView AddBid;
    private CardView AddVehicle;
    private CardView AddJobs;
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();

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
                if (mAuth.getCurrentUser() != null){
                    AddBidVehicleFragment addBidVehicleFragment = new AddBidVehicleFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.cont, addBidVehicleFragment,null);
                    fragmentTransaction.commit();
                }
                else {
                    showCustomDialog();
                }

            }
        });

        AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null){
                    VehiclesFragment vehiclesFragment = new VehiclesFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.cont, vehiclesFragment,null);
                    fragmentTransaction.commit();
                }
                else {
                    showCustomDialog();
                }

            }
        });

        AddJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAuth.getCurrentUser() != null){
                    AddJobFragment addJobFragment = new AddJobFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.cont, addJobFragment,null);
                    fragmentTransaction.commit();
                }
                else {
                    showCustomDialog();
                }

            }
        });

    }
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,profileFragment,null);
                fragmentTransaction.commit();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
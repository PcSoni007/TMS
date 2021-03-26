package com.prakhar.tms.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prakhar.tms.MainActivity;
import com.prakhar.tms.R;
import com.prakhar.tms.adapters.BidVehicleAdapter;
import com.prakhar.tms.adapters.UserAdapter;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Demo extends AppCompatActivity implements UserAdapter.OnItemClickListner{

    RecyclerView recyclerView;
    BiddingVehicle vehicles;

    private DatabaseReference myRef;
    private ProgressDialog progressDialog;
    LinearLayoutManager mLayoutManager;


    UserAdapter userAdapter;

    private String TAG = "Demo";
    private ArrayList<BiddingVehicle> list = new ArrayList<BiddingVehicle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeListView();

        progressDialog = new ProgressDialog(Demo.this);
//        progressDialog.setMessage("Loading Products Please Wait...");
        progressDialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

       /* Log.e(TAG, list.get(0).getBiddingTitle()+"");
        Log.e(TAG, list.get(1).getBiddingTitle().toString());*/

        userAdapter = new UserAdapter(Demo.this, list, Demo.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(userAdapter);
    }

    /*public void getData(){
        myRef = FirebaseDatabase.getInstance();
        mdatabasereference = myRef.getReference().child("bid_vehicle_details");
        mdatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("ValueEvent", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void initializeListView() {
        // creating a new array adapter for our list view.
//        adapter = new ArrayAdapter<Jobs>(getContext() , android.R.layout.simple_dropdown_item_1line, list);

        // below line is used for getting reference
        // of our Firebase Database.
        myRef = FirebaseDatabase.getInstance().getReference().child("bid_vehicle_details");

        // in below line we are calling method for add child event
        // listener to get the child of our database.
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added to
                // our data base and after adding new child
                // we are adding that iteBim inside our array list and
                // notifying our adapter that the data in adapter is changed.
                list.add(snapshot.getValue(BiddingVehicle.class));

                Log.e(TAG, snapshot.getValue()+"");
                Log.e(TAG, snapshot.getValue(BiddingVehicle.class).getBiddingTitle().toString());

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when the new child is added.
                // when the new child is added to our list we will be
                // notifying our adapter that data has changed.
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // below method is called when we remove a child from our database.
                // inside this method we are removing the child from our array list
                // by comparing with it's value.
                // after removing the data we are notifying our adapter that the
                // data has been changed.
                list.remove(snapshot.getValue(BiddingVehicle.class));
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when we move our
                // child in our database.
                // in our code we are note moving any child.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // this method is called when we get any
                // error from Firebase with error.
            }
        });
        // below line is used for setting
        // an adapter to our list view.
    }

    @Override
    public void onItemClick(int id) {

    }
}

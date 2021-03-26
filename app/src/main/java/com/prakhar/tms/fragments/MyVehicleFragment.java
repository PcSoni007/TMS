package com.prakhar.tms.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.adapters.BidVehicleAdapter;
import com.prakhar.tms.adapters.MyVehicleAdapter;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.VehicleDetails;
import com.prakhar.tms.utils.Tools;
import com.prakhar.tms.widgets.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

class MyVehicleFragment extends Fragment implements MyVehicleAdapter.OnItemClickListner{

    private View view;
    RecyclerView recyclerView;
    DatabaseReference myRef;
    StorageReference storageReference;
    List<VehicleDetails> list;
    List<StorageReference> storageReferencesList;
    MyVehicleAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        list = new ArrayList<VehicleDetails>();
        myRef = FirebaseDatabase.getInstance().getReference().child("vehicle_details");
        Log.e("My Vehicle Img", storageReference.getName().toString());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);

        storageReference = FirebaseStorage.getInstance().getReference().child("images/vehicle_image");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("ImageUri", uri.toString());

                    }
                });
                storageReferencesList = listResult.getItems();
            }
        }).addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BiddingVehicle bid = snapshot.getValue(BiddingVehicle.class);
                        if (bid != null) {
                            Log.e("BidVehicleList", snapshot.getValue().toString());
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                // TODO: handle the post
                                list.add(postSnapshot.getValue(VehicleDetails.class));
                                Log.e("BidVehicleList", postSnapshot.getValue().toString());
//                        Log.e("BidVehicleList", postSnapshot.getValue(BiddingVehicle.class).getBiddingDes().toString());


                            }
//                    Log.e("BidVehicleList", bid.getBiddingDes().toString());
                            adapter = new MyVehicleAdapter(getContext(), list, MyVehicleFragment.this, storageReferencesList);
                            recyclerView.setAdapter(adapter);

                        }
                        else {
                            Log.e("BidVehicleList", "Data not availble");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("BidVehicleList", "You are not permitted to access this data");
                    }
                });
            }
        });


        initComponent();


        return view;
    }

    private void initComponent() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        recyclerView.setHasFixedSize(true);


    }


    @Override
    public void onItemClick(int id) {

    }
}
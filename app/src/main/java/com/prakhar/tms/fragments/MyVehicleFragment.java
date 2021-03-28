package com.prakhar.tms.fragments;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class MyVehicleFragment extends Fragment implements MyVehicleAdapter.OnItemClickListner{

    private View view;
    RecyclerView recyclerView;
    DatabaseReference myRef;
    StorageReference storageReference;
    List<VehicleDetails> list;
    FirebaseAuth mAuth;
    List<StorageReference> storageReferencesList;
    MyVehicleAdapter adapter;
    private String TAG= "MyVehicleFragment";
    String CurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        list = new ArrayList<VehicleDetails>();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("vehicle_details");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);

        CurrentUser = mAuth.getUid();
//        Log.e("My Vehicle Img", storageReference.getName().toString());
        LinearLayout lyt_progress = (LinearLayout) view.findViewById(R.id.lyt_progress);

        CurrentUser = mAuth.getUid();
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
        initComponent();
        recyclerView.setVisibility(View.GONE);


        storageReference = FirebaseStorage.getInstance().getReference().child("images/vehicle_image");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e(TAG+"ImageUri", uri.toString());

                    }
                });
                List<StorageReference> myList = listResult.getItems();
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
                            Log.e(TAG, snapshot.getValue().toString());
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                // TODO: handle the post
                                VehicleDetails vehicleDetails = postSnapshot.getValue(VehicleDetails.class);
                                Log.e(TAG, vehicleDetails.getUserId().toString());
//                                Log.e(TAG, CurrentUser);
                                list.add(postSnapshot.getValue(VehicleDetails.class));
                                /*if(CurrentUser.equals(vehicleDetails.getUserId())){
                                    Log.e(TAG, postSnapshot.getValue().toString());
                                }*/
                                Log.e(TAG, postSnapshot.getValue().toString());
//                        Log.e("BidVehicleList", postSnapshot.getValue(BiddingVehicle.class).getBiddingDes().toString());

                            }
//                    Log.e("BidVehicleList", bid.getBiddingDes().toString());
                            recyclerView.setVisibility(View.VISIBLE);
                            lyt_progress.setVisibility(View.GONE);
                            adapter = new MyVehicleAdapter(getContext(), list, MyVehicleFragment.this, storageReferencesList, mAuth.getCurrentUser().toString());
                            recyclerView.setAdapter(adapter);

                        }
                        else {
                            Log.e(TAG, "Data not availble");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "You are not permitted to access this data");
                    }
                });
            }
        });


        return view;
    }

    private void initComponent() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        recyclerView.setHasFixedSize(true);


    }


    @Override
    public void onItemClick(int id) {

    }


}
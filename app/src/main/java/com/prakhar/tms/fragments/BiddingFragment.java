package com.prakhar.tms.fragments;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.adapters.AdapterGridTwoLineLight;
import com.prakhar.tms.adapters.BidVehicleAdapter;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.prakhar.tms.utils.Tools;
import com.prakhar.tms.utils.ViewAnimation;
import com.prakhar.tms.widgets.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class BiddingFragment extends Fragment implements BidVehicleAdapter.OnItemClickListner{

    RecyclerView recyclerView;
    BidVehicleAdapter adapter;
    DatabaseReference myRef;
    StorageReference storageReference;
    List<BiddingVehicle> list;
    private View view;
    List<StorageReference> storageReferencesList;
    CircularProgressIndicator progressIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        list = new ArrayList<BiddingVehicle>();
        myRef = FirebaseDatabase.getInstance().getReference().child("bid_vehicle_details");
        storageReference = FirebaseStorage.getInstance().getReference().child("images/bid_vehicle_images");
        Log.e("BiddingImg", storageReference.getName().toString());
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
        view = inflater.inflate(R.layout.fragment_bidding, container, false);

        LinearLayout lyt_progress = (LinearLayout) view.findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
        initComponent();
        recyclerView.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference().child("images/bid_vehicle_image");
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
                                list.add(postSnapshot.getValue(BiddingVehicle.class));
                                Log.e("BidVehicleList", postSnapshot.getValue().toString());
//                        Log.e("BidVehicleList", postSnapshot.getValue(BiddingVehicle.class).getBiddingDes().toString());


                            }
//                    Log.e("BidVehicleList", bid.getBiddingDes().toString());
                            adapter = new BidVehicleAdapter(getContext(), list, BiddingFragment.this, storageReferencesList);
                            recyclerView.setVisibility(View.VISIBLE);
                            lyt_progress.setVisibility(View.GONE);
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
/*
        set data and list adapter
        mAdapter = new AdapterGridTwoLineLight(getContext(), BidImgs, myRef);
        recyclerView.setAdapter(mAdapter);

         on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, StorageReference obj, int position) {
                Snackbar.make(parent_view,   " clicked", Snackbar.LENGTH_SHORT).show();

            }

        });
*/



    /*private RecyclerView recyclerView;
    private AdapterGridTwoLineLight mAdapter;
    private View parent_view;
    private View view;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    List<StorageReference> BidImgs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bidding, container, false);

//        parent_view = view.findViewById(android.R.id.content);
        storageRef = storage.getReference().child("images/bid_vehicle_image/");
        storageRef.listAll().addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                BidImgs = listResult.getItems();
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NoResultFragment noResultFragment = new NoResultFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, noResultFragment,null);
                fragmentTransaction.commit();
            }
        });

        initComponent();
        return view;
    }


    private void initComponent() {
        recyc lerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        recyclerView.setHasFixedSize(true);


//        set data and list adapter
        mAdapter = new AdapterGridTwoLineLight(getContext(), BidImgs, myRef);
        recyclerView.setAdapter(mAdapter);

//         on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridTwoLineLight.OnItemClickListener() {
            @Override
            public void onItemClick(View view, StorageReference obj, int position) {
                Snackbar.make(parent_view,   " clicked", Snackbar.LENGTH_SHORT).show();

            }

        });

    }
*/



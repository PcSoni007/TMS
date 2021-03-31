package com.prakhar.tms.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.prakhar.tms.R;
import com.prakhar.tms.adapters.UserAdapter;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.UserDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserProfileFragment extends Fragment {

    private View view;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    StorageReference storageReference;
    UserDetails userDetails;

    TextView UName;
    TextView UEmail;
    TextView UAdd;
    TextView UCont;
    TextView UAadhar;
    TextView UPan;
    TextView UId;
    TextView UType;
    CircularImageView UImg;

    Uri ImgUrl;

    String UserName;
    String UserEmail;
    String UserAdd;
    String UserCont;
    String UserEmgCont;
    String UserAadhar;
    String UserPan;
    String UserDl;
    String UserType;
    String UserImgUrl;

    private String CurrentUser;

    String UserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CurrentUser = mAuth.getCurrentUser().toString();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("user_details");
        storageReference = FirebaseStorage.getInstance().getReference().child("images/user_images");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_profile, container, false);

        initComp();
        getData();

        return view;
    }

    void initComp(){
        UName = view.findViewById(R.id.uName);
        UAdd = view.findViewById(R.id.uAdd);
        UEmail = view.findViewById(R.id.uEmail);
        UCont = view.findViewById(R.id.uCont);
        UAadhar = view.findViewById(R.id.uAadhar);
        UPan = view.findViewById(R.id.uPan);
        UId = view.findViewById(R.id.uId);
        UType = view.findViewById(R.id.uType);
        UImg = view.findViewById(R.id.uDp);

    }

    private void setData() {

        Log.e("CompSetting Data", "Initialze");

        UName.setText(UserName);
        UAdd.setText(UserAdd);

        UEmail.append(UserEmail);
        UCont.append(UserCont+","+UserEmgCont);
        UAadhar.append(UserAadhar);
        UPan.append(UserPan);
        UId.append(UserDl);
        UType.append(UserType);


        Log.e(  "CompSetting Data", "Finished");

        Picasso.get().load(ImgUrl).into(UImg);

    }

    void getData(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.e("UserProfile", "Current User"+snapshot.getKey());
                    Log.e("UserProfile", "Current User"+snapshot.getChildren().toString());

                    if(dataSnapshot.getKey().equals(CurrentUser)){
                        Log.e("UserProfile", "Current User"+dataSnapshot.getKey());
                    }
                    else {
                        Log.e("UserProfile", "Can't find Current User");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

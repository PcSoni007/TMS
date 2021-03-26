package com.prakhar.tms.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.VehicleDetails;
import com.prakhar.tms.utils.ViewAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public  class AddBidVehicleFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final int MAX_STEP = 4;
    private int current_step = 0;
    private ProgressBar progressBar;
    private TextView status;
    private View view;
    private ArrayList<String> tabs = new ArrayList<String>();

    EditText BidTitle;
    EditText BidPrice;
    EditText BidDes;


    EditText Vname;
    EditText Vno;
    EditText Vmodel;

    Spinner Vtype;
    EditText Vtire;
    EditText Average;
    EditText Vload;
    EditText Vavail;

    EditText Vowner;
    EditText Vloc;
    EditText Vcontact;

    CardView ImagePicker;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    Button UploadDetails;

    String[] Vtypes = {"Hydraulic", "Non Hydraulic"};
    String VehicleName;
    String VehicleNo;
    String VehicleModel;

    String BidddingTitle;
    String BidddingPrice;
    String BidddingDes;

    String VehicleType;
    String VehicleAvail;
    String VehicleTire;
    String VehicleLoad;
    String VehicleAvg;

    String VehicleOwner;
    String OwnerLoc;
    String OwnerCont;

    String UserId;


    private Uri filePath;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    String str_progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabs.add("Bid Details");
        tabs.add("Basic Details");
        tabs.add("Vehicle Specifications");
        tabs.add("Vehicle Owner Details");
        tabs.add("Vehicle Pics");

        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_bid_vehicle, container, false);
        init();
        initComponent();

        BidTitle.setVisibility(View.VISIBLE);
        BidDes.setVisibility(View.VISIBLE);
        BidPrice.setVisibility(View.VISIBLE);

        return view;
    }

    private void init(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            UserId = mAuth.getCurrentUser().getUid();
        }
        else{
            showCustomDialog();
        }

        BidTitle = view.findViewById(R.id.bidTitle);
        BidPrice = view.findViewById(R.id.bidPrice);
        BidDes = view.findViewById(R.id.bidDes);

        Vname = view.findViewById(R.id.vName);
        Vno = view.findViewById(R.id.vNo);
        Vmodel = view.findViewById(R.id.vModel);

        Vavail = view.findViewById(R.id.vAvail);
        Vtire = view.findViewById(R.id.vNoTire);
        Vload = view.findViewById(R.id.vLoad);
        Average = view.findViewById(R.id.average);
        Vtype = (Spinner) view.findViewById(R.id.vType);

        Vowner = view.findViewById(R.id.vOwner);
        Vloc = view.findViewById(R.id.oLocation);
        Vcontact = view.findViewById(R.id.oContact);

        ImagePicker = view.findViewById(R.id.imagePicker);
        imageView1 = view.findViewById(R.id.imageView1);

        UploadDetails = view.findViewById(R.id.upload);


        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Vtypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Vtype.setAdapter(types);
        Vtype.setOnItemSelectedListener(AddBidVehicleFragment.this);

    }

    private void initComponent() {
        status = (TextView) view.findViewById(R.id.status);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        ImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0000);
            }
        });

        UploadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadDetails(view);
            }
        });

        ((LinearLayout) view.findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
                CurrentView(current_step);

            }
        });

        ((LinearLayout) view.findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
                CurrentView(current_step);

            }
        });

//        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        str_progress = tabs.get(current_step);
        status.setText(str_progress);
    }

    private void nextStep(int progress) {
        if (progress < MAX_STEP) {
            progress++;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
//        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        str_progress = tabs.get(current_step);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }

    private void backStep(int progress) {
        if (progress > 0) {
            progress--;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
//        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        str_progress = tabs.get(current_step);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        VehicleType = Vtypes[pos].toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        VehicleType = "Na";
    }

    public void CurrentView(int c_step){

        if (c_step == 0){

            BidTitle.setVisibility(View.VISIBLE);
            BidDes.setVisibility(View.VISIBLE);
            BidPrice.setVisibility(View.VISIBLE);

            Vname.setVisibility(View.GONE);
            Vmodel.setVisibility(View.GONE);
            Vno.setVisibility(View.GONE);

            Vtype.setVisibility(View.GONE);
            Vtire.setVisibility(View.GONE);
            Vload.setVisibility(View.GONE);
            Average.setVisibility(View.GONE);
            Vavail.setVisibility(View.GONE);

            Vowner.setVisibility(View.GONE);
            Vloc.setVisibility(View.GONE);
            Vcontact.setVisibility(View.GONE);

            ImagePicker.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            UploadDetails.setVisibility(View.GONE);


        }

        else if (c_step == 1){

            BidTitle.setVisibility(View.GONE);
            BidDes.setVisibility(View.GONE);
            BidPrice.setVisibility(View.GONE);

            Vname.setVisibility(View.VISIBLE);
            Vmodel.setVisibility(View.VISIBLE);
            Vno.setVisibility(View.VISIBLE);

            Vtype.setVisibility(View.GONE);
            Vtire.setVisibility(View.GONE);
            Vload.setVisibility(View.GONE);
            Average.setVisibility(View.GONE);
            Vavail.setVisibility(View.GONE);

            Vowner.setVisibility(View.GONE);
            Vloc.setVisibility(View.GONE);
            Vcontact.setVisibility(View.GONE);

            ImagePicker.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            UploadDetails.setVisibility(View.GONE);

        }

        else if (c_step == 2){

            BidTitle.setVisibility(View.GONE);
            BidDes.setVisibility(View.GONE);
            BidPrice.setVisibility(View.GONE);

            Vname.setVisibility(View.GONE);
            Vmodel.setVisibility(View.GONE);
            Vno.setVisibility(View.GONE);

            Vtype.setVisibility(View.VISIBLE);
            Vtire.setVisibility(View.VISIBLE);
            Vload.setVisibility(View.VISIBLE);
            Average.setVisibility(View.VISIBLE);
            Vavail.setVisibility(View.VISIBLE);

            Vowner.setVisibility(View.GONE);
            Vloc.setVisibility(View.GONE);
            Vcontact.setVisibility(View.GONE);

            ImagePicker.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            UploadDetails.setVisibility(View.GONE);


        }

        else if (c_step == 3){

            BidTitle.setVisibility(View.GONE);
            BidDes.setVisibility(View.GONE);
            BidPrice.setVisibility(View.GONE);

            Vname.setVisibility(View.GONE);
            Vmodel.setVisibility(View.GONE);
            Vno.setVisibility(View.GONE);

            Vtype.setVisibility(View.GONE);
            Vtire.setVisibility(View.GONE);
            Vload.setVisibility(View.GONE);
            Average.setVisibility(View.GONE);
            Vavail.setVisibility(View.GONE);

            Vowner.setVisibility(View.VISIBLE);
            Vloc.setVisibility(View.VISIBLE);
            Vcontact.setVisibility(View.VISIBLE);

            ImagePicker.setVisibility(View.GONE);

            imageView1.setVisibility(View.GONE);
            UploadDetails.setVisibility(View.GONE);


        }
        else if( c_step == 4){

            BidTitle.setVisibility(View.GONE);
            BidDes.setVisibility(View.GONE);
            BidPrice.setVisibility(View.GONE);

            Vname.setVisibility(View.GONE);
            Vmodel.setVisibility(View.GONE);
            Vno.setVisibility(View.GONE);

            Vtype.setVisibility(View.GONE);
            Vtire.setVisibility(View.GONE);
            Vload.setVisibility(View.GONE);
            Average.setVisibility(View.GONE);
            Vavail.setVisibility(View.GONE);

            Vowner.setVisibility(View.GONE);
            Vloc.setVisibility(View.GONE);
            Vcontact.setVisibility(View.GONE);

            ImagePicker.setVisibility(View.VISIBLE);

        }


    }

    /*public void SelectImages(View view) {
     *//*ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .start();*//*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0000);

    }*/

    public boolean checkInput(int pos){
        if (pos == 0){
            if(Vno.getText().toString().equals("")){
                Vno.setError("Required");
                return false;
            }
            else
            {
                VehicleNo = Vno.getText().toString();
                return true;
            }
        }

        else if (pos == 1){
            return true;
        }

        else if (pos == 2){

            return true;
        }

        else if (pos == 3){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0000 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView1.setImageBitmap(bitmap);
                imageView1.setVisibility(View.VISIBLE);
                UploadDetails.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void UploadDetails(View view) {

        BidddingTitle = BidTitle.getText().toString();
        BidddingPrice = BidPrice.getText().toString();
        BidddingDes = BidDes.getText().toString();

        VehicleNo = Vno.getText().toString();
        VehicleName = Vname.getText().toString();
        VehicleModel = Vmodel.getText().toString();


        VehicleOwner = Vowner.getText().toString();
        VehicleAvg = Average.getText().toString();
        VehicleAvail = Vavail.getText().toString();
        VehicleTire = Vtire.getText().toString();
        VehicleLoad = Vload.getText().toString();

//        VehicleDetails mVehicle = new VehicleDetails(UserId, VehicleName, VehicleNo, VehicleType, VehicleAvg, "Available", VehicleTire, VehicleAvail, VehicleLoad, VehicleOwner, "Temporary Name" );
        BiddingVehicle mVehicle = new BiddingVehicle(BidddingTitle, BidddingDes, BidddingPrice, VehicleName, VehicleModel, VehicleNo, VehicleType, VehicleTire, VehicleLoad, VehicleAvail, VehicleAvg, VehicleOwner, OwnerCont, OwnerLoc, UserId);
        myRef.child("bid_vehicle_details").child(VehicleNo).setValue(mVehicle);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/bid_vehicle_image/"+VehicleNo);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Vehicle Added For Bidding", Toast.LENGTH_SHORT).show();
                            BiddingFragment biddingFragment = new BiddingFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cont,biddingFragment,null);
                            fragmentTransaction.commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                            Toast.makeText(getActivity(), "Vehicle Added for Bidding", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

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
                Toast.makeText(getContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
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
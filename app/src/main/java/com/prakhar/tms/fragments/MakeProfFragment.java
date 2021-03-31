package com.prakhar.tms.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.UserDetails;
import com.prakhar.tms.utils.ViewAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class MakeProfFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    AppCompatEditText Uname;
    AppCompatEditText Udescription;
    AppCompatEditText Uemail;
    AppCompatEditText UAdd;
    AppCompatEditText Ucont;
    AppCompatEditText UEmgCont;
    AppCompatEditText UAadhar;
    AppCompatEditText Upan;
    AppCompatEditText UGST;
    AppCompatEditText Udl;
    Spinner UType;
    String[] Utypes = {"Driver", "Truck Owner", "Tansporter"};
    String UserName;
    String UserEmail;
    String UserAdd;
    String UserCont;
    String UserEmgCont;
    String UserAadhar;
    String UserPan;
    String UserDl;
    String UserType;
    String UserGst;
    String UserId;
    ImageView imageView;
    CardView cardView;

    TextView Pdetails;
    TextView Cdetails;
    TextView Idetails;
    TextView Pics;
    TextView Confirmation;

    Context context;
    Button Continue1;
    Button Continue2;
    Button Continue3;
    Button Continue4;
    Button Continue5;

    private Uri filePath;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    CardView UimgPicker;
    private List<View> view_list = new ArrayList<>();
    private List<RelativeLayout> step_view_list = new ArrayList<>();
    private int success_step = 0;
    private int current_step = 0;
    private View parent_view;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        parent_view = view.findViewById(android.R.id.content);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        context = getContext();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_make_prof, container, false);

        initComponent();

        init();

        return view;
    }


    private void initComponent() {
        // populate layout field
        view_list.add(view.findViewById(R.id.lyt_title));
        view_list.add(view.findViewById(R.id.lyt_description));
        view_list.add(view.findViewById(R.id.lyt_time));
        view_list.add(view.findViewById(R.id.lyt_date));
        view_list.add(view.findViewById(R.id.lyt_confirmation));

        // populate view step (circle in left)
        step_view_list.add(((RelativeLayout) view.findViewById(R.id.step_title)));
        step_view_list.add(((RelativeLayout) view.findViewById(R.id.step_description)));
        step_view_list.add(((RelativeLayout) view.findViewById(R.id.step_time)));
        step_view_list.add(((RelativeLayout) view.findViewById(R.id.step_date)));
        step_view_list.add(((RelativeLayout) view.findViewById(R.id.step_confirmation)));

        Pdetails = view.findViewById(R.id.tv_label_title);
        Cdetails = view.findViewById(R.id.tv_label_description);
        Idetails = view.findViewById(R.id.tv_label_time);
        Pics = view.findViewById(R.id.tv_label_date);
        Confirmation = view.findViewById(R.id.tv_label_confirmation);

        for (View v : view_list) {
            v.setVisibility(View.GONE);
        }

        view_list.get(0).setVisibility(View.VISIBLE);
        hideSoftKeyboard();


    }

    private void init() {
        Uname = view.findViewById(R.id.userName);
        Uemail = view.findViewById(R.id.userEmail);
        Udescription = view.findViewById(R.id.userDescription);
        UAdd = view.findViewById(R.id.userAdd);
        Ucont = view.findViewById(R.id.userCont);
        UEmgCont = view.findViewById(R.id.userEmgCont);
        UAadhar = view.findViewById(R.id.userAadhar);
        Upan = view.findViewById(R.id.userPan);
        UGST = view.findViewById(R.id.userGst);
        Udl = view.findViewById(R.id.userDl);
        UType = (Spinner) view.findViewById(R.id.userType);
        imageView = view.findViewById(R.id.img);
        UimgPicker = view.findViewById(R.id.uImagePicker);

        Continue1 = view.findViewById(R.id.bt_continue_title);
        Continue2 = view.findViewById(R.id.bt_continue_description);
        Continue3 = view.findViewById(R.id.bt_continue_time);
        Continue4 = view.findViewById(R.id.bt_continue_date);
        Continue5 = view.findViewById(R.id.bt_add_event);

        Continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput(0)){
                    clickAction(view);
                }
            }
        });

        Continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput(1)){
                    clickAction(view);
                }
            }
        });
        Continue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput(2)){
                    clickAction(view);
                }
            }
        });
        Continue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath != null){
                    clickAction(view);
                }
            }
        });
        Continue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadDetails(view);

            }
        });

        UimgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImages(view);
            }
        });

        Pdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLabel(view);
            }
        });

        Cdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLabel(view);
            }
        });

        Idetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLabel(view);
            }
        });

        Pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLabel(view);
            }
        });

        Confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLabel(view);
            }
        });
        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Utypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UType.setAdapter(types);
        UType.setOnItemSelectedListener(MakeProfFragment.this);

    }


    public void clickAction(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_continue_title:
                // validate input user here

                collapseAndContinue(0);
                break;
            case R.id.bt_continue_description:
                collapseAndContinue(1);
                if (UserType.equals("Driver")) {    
                    Udl.setVisibility(View.VISIBLE);
                }
                else {
                    UGST.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_continue_time:
                // validate input user here

                collapseAndContinue(2);
                Udl.setVisibility(View.GONE);
                UGST.setVisibility(View.GONE);
                UimgPicker.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_continue_date:
                // validate input user here

                collapseAndContinue(3);
                UimgPicker.setVisibility(View.GONE);
                break;
            case R.id.bt_add_event:
                UploadDetails(view);
                break;
        }
    }

    public void clickLabel(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_label_title:
                if (success_step >= 0 && current_step != 0) {
                    current_step = 0;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(0));
                }
                break;
            case R.id.tv_label_description:
                if (success_step >= 1 && current_step != 1) {
                    current_step = 1;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(1));
                }
                break;
            case R.id.tv_label_time:
                if (success_step >= 2 && current_step != 2) {
                    current_step = 2;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(2));
                }
                break;
            case R.id.tv_label_date:
                if (success_step >= 3 && current_step != 3) {
                    current_step = 3;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(3));
                }
                break;
            case R.id.tv_label_confirmation:
                if (success_step >= 4 && current_step != 4) {
                    current_step = 4;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(4));
                }
                break;
        }
    }

    private void collapseAndContinue(int index) {
        ViewAnimation.collapse(view_list.get(index));
        setCheckedStep(index);
        index++;
        current_step = index;
        success_step = index > success_step ? index : success_step;
        ViewAnimation.expand(view_list.get(index));
    }

    private void collapseAll() {
        for (View v : view_list) {
            ViewAnimation.collapse(v);
        }
    }

    private void setCheckedStep(int index) {
        RelativeLayout relative = step_view_list.get(index);
        relative.removeAllViews();
        ImageButton img = new ImageButton(getContext());
        img.setImageResource(R.drawable.ic_done);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        relative.addView(img);
    }


    public void hideSoftKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        UserType = Utypes[pos].toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        UserType = "Na";
    }

    public void SelectImages(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0000 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkInput(int pos){

        if (pos == 0){
            if(Uname.getText().toString().equals("")){
                Uname.setError("Required");
                return false;
            }
            else {
                Uname.setEnabled(true);
            }

            if(UAdd.getText().toString().equals("")){
                UAdd.setError("Required");
                return false;
            }
            else {
                UAdd.setEnabled(true);
            }

            if(Udescription.getText().toString().equals("")){
                Udescription.setError("Required");
                return false;
            }
            else {
                Udescription.setEnabled(true);
            }

        }

        else if (pos == 1){

            if(Uemail.getText().toString().equals("")){
                Uemail.setError("Required");
                return false;
            }
            else {
                Uemail.setEnabled(true);
            }

            if(Ucont.getText().toString().equals("")){
                Ucont.setError("Required");
                return false;
            }
            else {
                Ucont.setEnabled(true);
            }

            if(UEmgCont.getText().toString().equals("")){
                UEmgCont.setError("Required");
                return false;
            }
            else {
                UEmgCont.setEnabled(true);
            }

        }

        else if (pos == 2){
            if(UAadhar.getText().toString().equals("")){
                UAadhar.setError("Required");
                return false;
            }
            else {
                UAadhar.setEnabled(true);
            }

            if(Upan.getText().toString().equals("")){
                Upan.setError("Required");
                return false;
            }
            else {
                Upan.setEnabled(true);
            }

            if(UserType.equals("Driver")){
                if(Udl.getText().toString().equals("")){
                    Udl.setError("Required");
                    return false;
                }
                else {
                    Udl.setEnabled(true);
                }
            }
            else {
                if(UGST.getText().toString().equals("")){
                    UGST.setError("Required");
                    return false;
                }
                else {
                    UGST.setEnabled(true);
                }
            }

        }
        return true;
    }

    public void UploadDetails(View view) {

        UserName = Uname.getText().toString();
        UserEmail = Uemail.getText().toString();
        UserAdd = UAdd.getText().toString();
        UserCont = Ucont.getText().toString();
        UserEmgCont = UEmgCont.getText().toString();
        UserAadhar = UAadhar.getText().toString();
        UserPan = Upan.getText().toString();
        UserDl = Udl.getText().toString();
        UserGst = UGST.getText().toString();

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/user_images/"+UserId);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "You Have Successfully Made Your Profile", Toast.LENGTH_SHORT).show();
                            UserDetails mUser = new UserDetails(UserType, UserEmail, UserName, UserAdd, UserCont, UserEmgCont, UserAadhar, UserPan, UserGst, UserDl);
                            myRef.child("user_details").child(UserId).setValue(mUser);
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cont, profileFragment, null);
                            fragmentTransaction.commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");


                        }
                    });
        }



    }
}
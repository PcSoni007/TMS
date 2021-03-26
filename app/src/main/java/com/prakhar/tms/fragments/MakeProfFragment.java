package com.prakhar.tms.fragments;

import android.app.ProgressDialog;
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
import android.widget.Toast;

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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_make_prof, container, false);
        init();
        initComponent();
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
                clickAction(view);
            }
        });

        Continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });
        Continue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });
        Continue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });
        Continue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAction(view);
            }
        });

        UimgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImages(view);
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
                /*if (((AppCompatEditText) view.findViewById(R.id.userName)).getText().toString().trim().equals("")) {
                    Snackbar.make(view, "Personal Details cannot be Empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }*/

                collapseAndContinue(0);
//                (BottomAppBar) view.findViewById(R.id.bottomAppBar).getFitsSystemWindows();
                break;
            case R.id.bt_continue_description:
                // validate input user here
                /*if (((AppCompatEditText) view.findViewById(R.id.userEmail)).getText().toString().trim().equals("")) {
                    Snackbar.make(view, "Contact Details cannot empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }*/

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

                /*if (((AppCompatEditText) view.findViewById(R.id.userAadhar)).getText().toString().trim().equals("")) {
                    Snackbar.make(view, "Contact Details cannot empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (((AppCompatEditText) view.findViewById(R.id.userPan)).getText().toString().trim().equals("")) {
                    Snackbar.make(view, "Contact Details cannot empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }*/
                if (UserType.equals("Driver")) {
//                    Udl.setVisibility(View.GONE);
                    /*if (((AppCompatEditText) view.findViewById(R.id.userDl)).getText().toString().trim().equals("")) {
                        Snackbar.make(view, "Contact Details cannot empty", Snackbar.LENGTH_SHORT).show();
                        return;
                    }*/
                }
                else {
//                    UGST.setVisibility(View.GONE);
                    /*if (((AppCompatEditText) view.findViewById(R.id.userGst)).getText().toString().trim().equals("")) {
                        Snackbar.make(parent_view, "Contact Details cannot empty", Snackbar.LENGTH_SHORT).show();
                        return;
                    }*/
                }
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
                // validate input user here
//                finish();
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

        UserDetails mUser = new UserDetails(UserType, UserEmail, UserName, UserAdd, UserCont, UserEmgCont, UserAadhar, UserPan, UserGst, UserDl);
        myRef.child("user_details").child(UserId).setValue(mUser);

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UserId + "/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "You Have Successfully Made Your Profile", Toast.LENGTH_SHORT).show();
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
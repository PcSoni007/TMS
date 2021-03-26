package com.prakhar.tms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

//import com.github.dhaval2404.imagepicker.ImagePicker;
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

import java.io.IOException;
import java.util.UUID;

public class AddUserDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Uname;
    EditText Uemail;
    EditText UAdd;
    EditText Ucont;
    EditText UEmgCont;
    EditText UAadhar;
    EditText Upan;
    EditText UGST;
    EditText Udl;
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
    private Uri filePath;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        init();
    }

    private void init(){
        Uname = findViewById(R.id.UserName);
        Uemail = findViewById(R.id.UserEmail);
        UAdd = findViewById(R.id.UserAdd);
        Ucont = findViewById(R.id.UserCont);
        UEmgCont = findViewById(R.id.EmgCont);
        UAadhar = findViewById(R.id.AadharNo);
        Upan = findViewById(R.id.PanNo);
        UGST = findViewById(R.id.UserGst);
        Udl = findViewById(R.id.DL);
        UType = (Spinner) findViewById(R.id.UserType);
        imageView = findViewById(R.id.img);

        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Utypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UType.setAdapter(types);
        UType.setOnItemSelectedListener(AddUserDetails.this);

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
        /*ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .start();*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0000 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
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

        UserDetails  mUser = new UserDetails(UserType, UserEmail, UserName, UserAdd, UserCont, UserEmgCont, UserAadhar, UserPan, UserGst, UserDl);
        myRef.child("user_details").child(UserId).setValue(mUser);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+UserId+"/"+UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddUserDetails.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddUserDetails.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }


}
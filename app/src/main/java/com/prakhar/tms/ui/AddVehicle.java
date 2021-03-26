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
import com.prakhar.tms.modals.VehicleDetails;

import java.io.IOException;
import java.util.UUID;

public class AddVehicle extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Vname;
    EditText Vno;
    Spinner Vtype;
    EditText Vtire;
    EditText Vnotire;
    EditText Vavail;
    EditText Vowner;
    EditText Vtov;
    EditText Vload;
    String[] Vtypes = {"Hydraulic", "Non Hydraulic"};
    String VehicleName;
    String VehicleNo;
    String VehicleType;
    String VehicleAvail;
    String VehicleOwner;
    String VehicleTire;
    String VehicleLoad;
    String VehicletToV;
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
        setContentView(R.layout.activity_add_vehicle);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        init();
    }

    private void init(){
        Vname = findViewById(R.id.vName);
        Vno = findViewById(R.id.vNo);
        Vnotire = findViewById(R.id.vNoTire);
        Vavail = findViewById(R.id.vAvail);
        Vowner = findViewById(R.id.vOwner);
        Vtire = findViewById(R.id.vNoTire);
        Vload = findViewById(R.id.vLoad);
        Vtov = findViewById(R.id.average);
        Vtype = (Spinner) findViewById(R.id.vType);
        imageView = findViewById(R.id.vImg);

        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Vtypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Vtype.setAdapter(types);
        Vtype.setOnItemSelectedListener(AddVehicle.this);

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
                imageView.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void UploadDetails(View view) {

        VehicleName = Vname.getText().toString();
        VehicleNo = Vno.getText().toString();
        if(VehicleNo == null){
            Vno.setError("Required");
        }
        VehicleOwner = Vowner.getText().toString();
        VehicletToV = Vtov.getText().toString();
        VehicleAvail = Vavail.getText().toString();
        VehicleTire = Vtire.getText().toString();
        VehicleLoad = Vload.getText().toString();

        VehicleDetails mVehicle = new VehicleDetails(UserId, VehicleName, VehicleNo, VehicleType, VehicletToV, "Available", VehicleTire, VehicleAvail, VehicleLoad );
        myRef.child("vehicle_details").child(VehicleNo).setValue(mVehicle);

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/vehicle_image/"+VehicleNo+"/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVehicle.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVehicle.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
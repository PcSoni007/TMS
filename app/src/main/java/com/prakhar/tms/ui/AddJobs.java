package com.prakhar.tms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.Jobs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddJobs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Jtitle;
    EditText JDes;
    EditText Gname;
    EditText Gdes;
    EditText Gquan;
    EditText Jsource;
    EditText Jdest;
    EditText Jstatus;
    EditText Jdate;
    EditText Jpay;
    Spinner Jtype;
    String[] jtypes = {"Full Time", "Part Time", "One Time", "Weekly"};
    // Job Detail Field
    private String JobTitle;
    private String JobDes;
    private String GoodsName;
    private String GoodsQuant;
    private String GoodsDes;
    private String Source;
    private String Destination;
    private String JobStatus;
    private String JobId;
    private String JobDate;
    private String JobPay;
    private String JobType;
    private String UserId;

    Button UploadBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);
        myCalendar = Calendar.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        init();

/*        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                JobTitle = Jtitle.getText().toString();
                JobDes = JDes.getText().toString();
                GoodsName = Gname.getText().toString();
                GoodsDes = Gdes.getText().toString();
                GoodsQuant = Gquan.getText().toString();
                Source = Jsource.getText().toString();
                Destination = Jdest.getText().toString();
                JobStatus = Jstatus.getText().toString();
                JobDate = Jdate.getText().toString();
                JobPay = Jpay.getText().toString();

                Jobs mJobs = new Jobs(UserId, JobTitle, JobDes, GoodsName, GoodsQuant, GoodsDes, Source, Destination, JobStatus, "123","JobDate", JobPay, JobType);

                myRef.child("job_details").setValue(mJobs).addOnFailureListener(AddJobs.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddJobs.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        Log.e("UploadDetails", e.getMessage().toString());
                    }
                });
            }
        });

        UploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.start();
            }
        });*/

        /*date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(D3;atePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };*/
    }

    private void init(){
        Jtitle = findViewById(R.id.jTitle);
        JDes = findViewById(R.id.jDes);
        Gname = findViewById(R.id.gName);
        Gquan = findViewById(R.id.gQuan);
        Gdes = findViewById(R.id.gDes);
        Jsource = findViewById(R.id.source);
        Jdest = findViewById(R.id.destionation);
        Jstatus = findViewById(R.id.jStatus);
        Jtype = (Spinner) findViewById(R.id.jType);
        Jpay =  findViewById(R.id.jPay);
        Jdate = findViewById(R.id.jDate);
        UploadBtn = findViewById(R.id.upload);


        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jtypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Jtype.setAdapter(types);
        Jtype.setOnItemSelectedListener(AddJobs.this);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        JobType = jtypes[pos].toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        JobType = "Na";
    }


    public void UploadDetails(View view) {
        JobTitle = Jtitle.getText().toString();
        JobDes = JDes.getText().toString();
        GoodsName = Gname.getText().toString();
        GoodsDes = Gdes.getText().toString();
        GoodsQuant = Gquan.getText().toString();
        Source = Jsource.getText().toString();
        Destination = Jdest.getText().toString();
        JobStatus = Jstatus.getText().toString();
        JobDate = Jdate.getText().toString();
        JobPay = Jpay.getText().toString();

        Jobs mJobs = new Jobs(UserId, JobTitle, JobDes, GoodsName, GoodsQuant, GoodsDes, Source, Destination, JobStatus, "123","JobDate", JobPay, JobType);

        myRef.child("job_details").child("12").setValue(mJobs).addOnFailureListener(AddJobs.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddJobs.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.e("UploadDetails", e.getMessage().toString());
            }
        });

    }
    private void Uploadthread(){

    }

    public void OpenDataPicker(View view) {
        new DatePickerDialog(AddJobs.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Jdate.setText(sdf.format(myCalendar.getTime()));
    }

}
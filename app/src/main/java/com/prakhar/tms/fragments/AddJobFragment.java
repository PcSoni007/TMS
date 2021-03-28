package com.prakhar.tms.fragments;

import android.app.Dialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prakhar.tms.R;
import com.prakhar.tms.modals.BiddingVehicle;
import com.prakhar.tms.modals.Jobs;
import com.prakhar.tms.utils.Tools;
import com.prakhar.tms.utils.ViewAnimation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AddJobFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Date date = null;
    private TextView tv;
    private static final int MAX_STEP = 2;
    private int current_step = 0;
    private ProgressBar progressBar;
    private TextView status;
    private View view;
    private ArrayList<String> tabs = new ArrayList<String>();

    EditText Jobtitle;
    EditText Jobdes;
    EditText Jobpay;
    Spinner Jobtype;


    EditText Goodsname;
    EditText Goodsdescription;
    EditText Goodsquantity;

    EditText Jobsource;
    EditText Jobdestination;
    EditText Jobstatus;
    EditText Jobid;

    Button UploadDetails;

    String[] Vtypes = {"Full Time", "Part Time", "One Time"};

    String JobTitle;
    String JobDate;
    String JobDes;
    String JobPay;
    String JobType;


    String GoodsName;
    String GoodsDescription;
    String GoodsQuantity;

    String JobSource;
    String JobDestination;
    String JobStatus;
    String JobId;

    String UserId;

    String str_progress;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabs.add("Job Details");
        tabs.add("Job Goods");
        tabs.add("About Job");

        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_job, container, false);
        init();
        initComponent();
        return view;
    }

     private void init(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        UserId = mAuth.getCurrentUser().getUid();

        Jobtitle = view.findViewById(R.id.jobTitle);
        Jobdes = view.findViewById(R.id.jobDes);
        Jobpay = view.findViewById(R.id.jobPay);
        Jobtype = (Spinner) view.findViewById(R.id.jobType);

        Goodsname = view.findViewById(R.id.goodsName);
        Goodsdescription = view.findViewById(R.id.goodsDes);
        Goodsquantity = view.findViewById(R.id.goodsQuant);

        Jobsource = view.findViewById(R.id.jobSource);
        Jobdestination = view.findViewById(R.id.jobDestination);
        Jobstatus = view.findViewById(R.id.jobStatus);
        Jobid = view.findViewById(R.id.jobId);


        UploadDetails = view.findViewById(R.id.upload);


        // Spinner User Type
        ArrayAdapter<String> types = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Vtypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Jobtype.setAdapter(types);
        Jobtype.setOnItemSelectedListener(AddJobFragment.this);

    }

    private void initComponent() {
        status = (TextView) view.findViewById(R.id.status);

        Jobtitle.setVisibility(View.VISIBLE);
        Jobdes.setVisibility(View.VISIBLE);
        Jobpay.setVisibility(View.VISIBLE);
        Jobpay.setVisibility(View.VISIBLE);
        Jobtype.setVisibility(View.VISIBLE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        tv = view.findViewById(R.id.tv_date);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePickerLight((TextView) v);
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
        JobType = Vtypes[pos].toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        JobType = "Na";
    }

    public void CurrentView(int c_step){

        if (c_step == 0){

            Jobtitle.setVisibility(View.VISIBLE);
            Jobdes.setVisibility(View.VISIBLE);
            Jobpay.setVisibility(View.VISIBLE);
            Jobpay.setVisibility(View.VISIBLE);
            Jobtype.setVisibility(View.VISIBLE);

            Goodsname.setVisibility(View.GONE);
            Goodsquantity.setVisibility(View.GONE);
            Goodsdescription.setVisibility(View.GONE);

            Jobsource.setVisibility(View.GONE);
            Jobdestination.setVisibility(View.GONE);
            Jobstatus.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);

            UploadDetails.setVisibility(View.GONE);
        }

        else if (c_step == 1){

            Jobtitle.setVisibility(View.GONE);
            Jobdes.setVisibility(View.GONE);
            Jobpay.setVisibility(View.GONE);
            Jobpay.setVisibility(View.GONE);
            Jobtype.setVisibility(View.GONE);

            Goodsname.setVisibility(View.VISIBLE);
            Goodsquantity.setVisibility(View.VISIBLE);
            Goodsdescription.setVisibility(View.VISIBLE);

            Jobsource.setVisibility(View.GONE);
            Jobdestination.setVisibility(View.GONE);
            Jobstatus.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
            UploadDetails.setVisibility(View.GONE);

        }

        else if (c_step == 2){

            Jobtitle.setVisibility(View.GONE);
            Jobdes.setVisibility(View.GONE);
            Jobpay.setVisibility(View.GONE);
            Jobpay.setVisibility(View.GONE);
            Jobtype.setVisibility(View.GONE);

            Goodsname.setVisibility(View.GONE);
            Goodsquantity.setVisibility(View.GONE);
            Goodsdescription.setVisibility(View.GONE);

            Jobsource.setVisibility(View.VISIBLE);
            Jobdestination.setVisibility(View.VISIBLE);
            Jobstatus.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);

            UploadDetails.setVisibility(View.VISIBLE);
        }



    }



    public void UploadDetails(View view) {

        JobTitle = Jobtitle.getText().toString();
        JobDes = Jobdes.getText().toString();
        JobPay = Jobpay.getText().toString();
        JobId = Jobid.getText().toString();
        JobDate= tv.getText().toString();

        GoodsName = Goodsname.getText().toString();
        GoodsDescription = Goodsdescription.getText().toString();
        GoodsQuantity = Goodsquantity.getText().toString();


        JobSource = Jobsource.getText().toString();
        JobDestination = Jobdestination.getText().toString();
        JobStatus = Jobstatus.getText().toString();

//        VehicleDetails mVehicle = new VehicleDetails(UserId, VehicleName, VehicleNo, VehicleType, VehicleAvg, "Available", VehicleTire, VehicleAvail, VehicleLoad, VehicleOwner, "Temporary Name" );
        Jobs jobs = new Jobs(UserId, JobTitle, JobDes, GoodsName, GoodsQuantity, GoodsDescription, JobSource, JobDestination, JobStatus, JobId, JobDate, JobPay, JobType   );
        myRef.child("job_details").child(UserId+date+JobId).setValue(jobs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Job Added Successfully", Toast.LENGTH_SHORT).show();
                JobsFragment jobsFragment = new JobsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,jobsFragment,null);
                fragmentTransaction.commit();
            }
        });



    }

    private void dialogDatePickerLight(final TextView tv) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        date = new Date(date_ship_millis);
                        tv.setText(Tools.getFormattedDateSimple(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


}
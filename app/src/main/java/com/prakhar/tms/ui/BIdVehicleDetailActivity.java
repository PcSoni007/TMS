package com.prakhar.tms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.prakhar.tms.R;
import com.squareup.picasso.Picasso;

public class BIdVehicleDetailActivity extends AppCompatActivity {

    private String BiddingTitle;
    private String BiddingDes;
    private String BiddingPrice;

    private String VehicleName;
    private String VehicleModel;
    private String VehicleNo;

    private String VehicleType;
    private String NoOfTires;
    private String LoadingCap;
    private String KmDriven;
    private String Average;

    private String VehicleOwner;
    private String OwnerCont;
    private String Ownerloc;

    private String ImgUrl;

    TextView BidTitle;
    TextView BidPrice;

    TextView Vmodel;

    TextView Vtype;
    TextView Vtire;
    TextView Vaverage;
    TextView Vload;
    TextView Vdriven;

    TextView Vowner;
    TextView Vloc;
    TextView Vcontact;

    EditText UBid;

    ImageView VehicleImg;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_id_vehicle_detail);
        Log.e("BidActivity", "Started");

        initToolbar();
        getData();
        initCompnents();
        setData();
    }


    private void initCompnents() {
        Log.e("CompInit", "Initialze");

        BidTitle = findViewById(R.id.biddingTitle);
        BidPrice = findViewById(R.id.bidStart);
        UBid = findViewById(R.id.vehicleBid);

        Vtype = findViewById(R.id.vehicleType);
        Vmodel = findViewById(R.id.vehicleModel);
        Vtire = findViewById(R.id.vehicletire);
        Vaverage = findViewById(R.id.vehicleAvg);
        Vload = findViewById(R.id.vehicleLoad);
        Vdriven = findViewById(R.id.vehicleKmDriven);

        Vowner = findViewById(R.id.ownerName);
        Vloc = findViewById(R.id.ownerAdd);
        Vcontact = findViewById(R.id.ownerContact);

        VehicleImg = findViewById(R.id.vehicleImg);

        Log.e("CompInit", "Finished");

    }

    private void setData() {

        Log.e("CompSetting Data", "Initialze");

        BidTitle.setText(BiddingTitle);
        UBid.setText(BiddingPrice);

        Vtype.append(VehicleType);
        Vmodel.append(VehicleName+" "+VehicleModel);
        Vtire.append(NoOfTires);
        Vaverage.append(Average);
        Vload.append(LoadingCap);
        Vdriven.append(KmDriven);

        Vowner.setText(VehicleOwner);
        Vcontact.setText(OwnerCont);
        Vloc.setText(Ownerloc);

        Log.e(  "CompSetting Data", "Finished");

        Picasso.get().load(ImgUrl).into(VehicleImg);

    }

    private void getData() {
        intent = getIntent();

        BiddingTitle = intent.getStringExtra("BiddingTitle");
        Log.e("DataFormInten", BiddingTitle+"");
        BiddingDes = intent.getStringExtra("BiddingDescription");
        BiddingPrice = intent.getStringExtra("BiddingStart");

        VehicleName = intent.getStringExtra("VehicleName");
        VehicleModel = intent.getStringExtra("VehicleModel");
        VehicleNo = intent.getStringExtra("VehicleNo");

        VehicleType = intent.getStringExtra("VehicleType");
        NoOfTires = intent.getStringExtra("VehicleTire");
        LoadingCap = intent.getStringExtra("VehicleLoadingCap");
        KmDriven = intent.getStringExtra("VehicleAvailability");
        Average = intent.getStringExtra("VehicleAverage");

        VehicleOwner = intent.getStringExtra("OwnerName");
        OwnerCont = intent.getStringExtra("OwnerContact");
        Ownerloc = intent.getStringExtra("OwnerLocation");

        ImgUrl = intent.getStringExtra("VehicleImg");

    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        removeExtra();
    }

    private void removeExtra() {
        intent.removeExtra("BiddingTitle");

        intent.removeExtra("BiddingDescription");
        intent.removeExtra("BiddingStart");

        intent.removeExtra("VehicleName");
        intent.removeExtra("VehicleModel");
        intent.removeExtra("VehicleNo");

        intent.removeExtra("VehicleType");
        intent.removeExtra("VehicleTire");
        intent.removeExtra("VehicleLoadingCap");
        intent.removeExtra("VehicleAvailability");
        intent.removeExtra("VehicleAverage");

        intent.removeExtra("OwnerName");
        intent.removeExtra("OwnerContact");
        intent.removeExtra("OwnerLocation");

    }

    public void AddBid(View view) {
        Toast.makeText(this, "You Have Added Your Bid Succesfully", Toast.LENGTH_SHORT).show();
        finish();
    }

}
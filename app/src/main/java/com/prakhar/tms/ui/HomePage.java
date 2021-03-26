package com.prakhar.tms.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.prakhar.tms.R;
import com.prakhar.tms.fragments.AddBidFragment;
import com.prakhar.tms.fragments.BiddingFragment;
import com.prakhar.tms.fragments.JobsFragment;
import com.prakhar.tms.fragments.ProfileFragment;
import com.prakhar.tms.fragments.VehiclesFragment;
import com.prakhar.tms.utils.Tools;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private ImageView img5;
    private ImageView img4;
    private FloatingActionButton fab;


//    private Button LoginBtn;
//    private Button SignupBtn;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        img1 = findViewById(R.id.menu_nav_1);
        img2 = findViewById(R.id.menu_nav_2);
        img4 = findViewById(R.id.menu_nav_4);
        img5 = findViewById(R.id.menu_nav_5);
        fab = findViewById(R.id.addBid);

        AddBidFragment addBidFragment = new AddBidFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cont, addBidFragment,null);
        fragmentTransaction.commit();

        initToolbar();
    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.blue_300);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomePage.this, "Bottom Navigation Icon 1", Toast.LENGTH_SHORT).show();
                BiddingFragment biddingFragment = new BiddingFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,biddingFragment,null);
                fragmentTransaction.commit();

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomePage.this, "Bottom Navigation Icon 2", Toast.LENGTH_SHORT).show();
                JobsFragment jobsFragment = new JobsFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,jobsFragment,null);
                fragmentTransaction.commit();
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomePage.this, "Bottom Navigation Icon 4", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, Demo.class);
                startActivity(intent);
                /*VehiclesFragment vehiclesFragment = new VehiclesFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, vehiclesFragment,null);
                fragmentTransaction.commit();*/
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomePage.this, "Bottom Navigation Icon 5", Toast.LENGTH_SHORT).show();
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,profileFragment,null);
                fragmentTransaction.commit();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBidFragment addBidding = new AddBidFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont,addBidding,null);
                fragmentTransaction.commit();
            }
        });

    }

}
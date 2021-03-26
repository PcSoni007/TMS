package com.prakhar.tms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.prakhar.tms.R;
import com.prakhar.tms.ui.HomePage;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {

    View view;
    private FirebaseAuth mAuth;
    private CircularImageView userDp;
    private TextView userName;
    private TextView userEmail;
    private Button LogoutBtn;
    private Button LoginBtn;
    private Button ProfileBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mAuth.getCurrentUser() != null){
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            initprof(view);
        }
        else{
            view = inflater.inflate(R.layout.fragment_user_login, container, false);
            initlogin(view);
        }


       return view;
    }

    private void initprof(View view) {
        userDp = view.findViewById(R.id.UserDp);
        userEmail = view.findViewById(R.id.userEmail);
        userName = view.findViewById(R.id.userName);
        LogoutBtn = view.findViewById(R.id.logout);
        ProfileBtn = view.findViewById(R.id.makeProfile);

        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeProfFragment makeProfFragment = new MakeProfFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, makeProfFragment,null);
                fragmentTransaction.commit();
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.logout) {
                    AuthUI.getInstance()
                            .signOut(Objects.requireNonNull(getContext()))
                            .addOnCompleteListener((OnCompleteListener<Void>) task -> {
                                // user is now signed out
                                Toast.makeText(getContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
                                ProfileFragment profileFragment = new ProfileFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.cont, profileFragment,null);
                                fragmentTransaction.commit();
                            });
                }
            }
        });

        userEmail.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        userName.setText(mAuth.getCurrentUser().getDisplayName());

        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(userDp);

    }
    private void initlogin(View view) {
        LoginBtn = view.findViewById(R.id.userLogin);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Choose authentication providers
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                /*AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                        .Builder(R.layout.activity_login)
                        .setGoogleButtonId(R.id.loginGoogle)
                        .setEmailButtonId(R.id.loginEmail)
                        .setPhoneButtonId(R.id.loginPhone)
                        .build();*/

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance(FirebaseApp.initializeApp(getContext()))
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setTheme(R.style.signup)
                                .build(), 123
                );
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == 123) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cont, profileFragment,null);
                fragmentTransaction.commit();
//                startActivity(SignedInActivity.createIntent(this, response));
//                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
//                    showSnackbar(R.string.sign_in_cancelled);
//                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
//                    showSnackbar(R.string.no_internet_connection);
//                    return;
                }

//                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }
}
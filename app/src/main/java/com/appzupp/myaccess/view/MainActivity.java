package com.appzupp.myaccess.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appzupp.myaccess.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final Context context = getApplicationContext();
        final FirebaseUser user = mAuth.getCurrentUser();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    Intent scanQR = new Intent(context, ScanQR.class);
                    startActivity(scanQR);
                    finish();
                } else {
                    Intent signIn = new Intent(context, GoogleSignIn.class);
                    startActivity(signIn);
                    finish();
                }

            }
        }, 5000);


    }
}
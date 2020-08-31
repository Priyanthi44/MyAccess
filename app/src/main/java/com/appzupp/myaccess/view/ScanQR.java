package com.appzupp.myaccess.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appzupp.myaccess.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ScanQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);
        ImageButton btnQR=findViewById(R.id.btnScan);
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                Intent displayList=new Intent(this, ActivityList.class);
                startActivity(displayList);
                finish();

            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }

    }

    public static class MainActivity extends AppCompatActivity {

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
}
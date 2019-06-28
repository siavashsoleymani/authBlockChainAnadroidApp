package com.example.qrcodereader.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qrcodereader.R;
import com.example.qrcodereader.util.ShakeListener;
import com.example.qrcodereader.util.ToastHelper;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 1234
            );
        }

        ShakeListener shakeListener = new ShakeListener(this);
        shakeListener.setOnShakeListener(() -> {
            Intent intent = new Intent(ScanActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}

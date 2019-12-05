package com.example.activitymodule;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.itzheng.and.activity.ItActivity;
import org.itzheng.and.activity.permissions.IPermissionManager;

public class MainActivity extends ItActivity {
    private static final String TAG = "MainActivity";
    public static boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isRunning = true;
        SplashActivity.startActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView(R.id.btnToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("nihao");
                setStatusBarColor(getResources().getColor(android.R.color.holo_red_dark));
                setStatusBarDarkMode(false);
            }
        });
        getView(R.id.btnPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPermission();
                setStatusBarColor(getResources().getColor(android.R.color.holo_blue_light));
                setStatusBarDarkMode(true);
            }
        });
        getView(R.id.btnKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeyboardActivity.class));
            }
        });
        getView(R.id.btnSwipeBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SwipeBackActivity.class);
            }
        });
        getView(R.id.btnLoading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoadingActivity.class);
            }
        });

    }

    private void testPermission() {
        requestPermissions(new IPermissionManager.OnRequestPermissionListener() {
            @Override
            public void grantPermissions(boolean b, String[] permissions) {
                Log.i(TAG, "grantPermissions: " + b);
                showToast("权限已打开");
            }

            @Override
            public void denyPermissions(boolean b, String[] permissions) {
                Log.w(TAG, "denyPermissions: " + b);
                showToast("权限被拒绝");
            }
        }, Manifest.permission.CAMERA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}

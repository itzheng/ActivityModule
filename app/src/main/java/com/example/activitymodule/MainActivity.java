package com.example.activitymodule;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import org.itzheng.and.activity.ItActivity;
import org.itzheng.and.activity.permissions.IPermissionManager;

public class MainActivity extends ItActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testPermission();
    }

    private void testPermission() {
        requestPermissions(new IPermissionManager.OnRequestPermissionListener() {
            @Override
            public void grantPermissions(boolean b, String[] permissions) {
                Log.i(TAG, "grantPermissions: " + b);
            }

            @Override
            public void denyPermissions(boolean b, String[] permissions) {
                Log.w(TAG, "denyPermissions: " + b);
            }
        }, Manifest.permission.CAMERA);
    }
}

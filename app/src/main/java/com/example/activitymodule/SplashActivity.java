package com.example.activitymodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public class SplashActivity extends org.itzheng.and.activity.SplashActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean fromManifest = isFromManifest(getIntent());
        Log.w(TAG, "onCreate: fromManifest:" + fromManifest);
        if (fromManifest) {
            if (MainActivity.isRunning) {
                //MainActivity 已经在运行了，就不再执行
                finish();
                return;
            }
//            App.getInstance().init();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            showSplash();
        }
    }

    /**
     * 启动Splash Activity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        startActivity(context, SplashActivity.class);
    }

}

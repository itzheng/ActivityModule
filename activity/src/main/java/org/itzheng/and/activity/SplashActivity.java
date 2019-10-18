package org.itzheng.and.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Title:启动页和splash应该是同一个界面，只是进行不同参数识别<br>
 * 在manifest设置主题，可以在主题中设置启动的背景颜色
 * <style name="LauncherTheme" parent="Theme.AppCompat.Light.NoActionBar">
 * <item name="android:windowBackground">@mipmap/ic_launcher</item>
 * <item name="android:windowFullscreen">true</item>
 * <item name="android:windowNoTitle">true</item>
 * <item name="android:windowContentOverlay">@android:color/white</item>
 * <item name="android:windowTranslucentNavigation" tools:targetApi="kitkat">true</item>
 * <item name="android:windowTranslucentStatus" tools:targetApi="kitkat">true</item>
 * <item name="android:navigationBarColor" tools:targetApi="lollipop">
 *
 * @android:color/transparent </item>
 * <item name="android:statusBarColor" tools:targetApi="lollipop">@android:color/transparent
 * </item>
 * </style>
 * Description: <br>
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-9-14.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
//        boolean fromManifest = isFromManifest(getIntent());
//        Log.w(TAG, "onCreate: fromManifest:" + fromManifest);
//        if (fromManifest) {
//            if (MainActivity.isRunning) {
//                //MainActivity 已经在运行了，就不再执行
//                finish();
//                return;
//            }
//            App.getInstance().init();
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        } else {
//            showSplash();
//        }
    }

    protected void showSplash() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private static final int REQUEST_CODE = 0x3837;

    /**
     * 启动Splash Activity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        isFromManifest(intent, false);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(intent, REQUEST_CODE);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    private static final String SER_BOOLEAN_IS_FROM_MANIFEST = "SER_BOOLEAN_IS_FROM_MANIFEST";

    /**
     * 是否是Splash,默认为系统启动
     *
     * @param intent
     * @param isFromManifest 是否来自Manifest，如果是手动启动的都不是
     */
    private static void isFromManifest(Intent intent, Boolean isFromManifest) {
        if (intent == null || isFromManifest == null) {
            return;
        }
        intent.putExtra(SER_BOOLEAN_IS_FROM_MANIFEST, isFromManifest);
    }

    private static final String TAG = "SplashActivity";

    /**
     * 是否是从manifest启动
     *
     * @param intent
     * @return
     */
    protected static boolean isFromManifest(Intent intent) {
        if (intent == null) {
            Log.w(TAG, "isFromManifest: intent == null ");
            return true;
        }
        return intent.getBooleanExtra(SER_BOOLEAN_IS_FROM_MANIFEST, true);
    }

    /**
     * Splash 结束监听需要在 onActivityResult 中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return 是否拦截
     */
    public static boolean isSplashEnd(int requestCode, int resultCode, Intent data) {
        if (requestCode == SplashActivity.REQUEST_CODE) {
            return true;
        }
        return false;
    }
}

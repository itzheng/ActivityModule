package org.itzheng.and.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Title:完全透明的Activity，避免启动时，看到最顶部的主题颜色<br>
 * Description: <br>
 * 在App 的onCreate中添加 setStartActivity();
 * <activity
 * android:name="org.itzheng.and.activity.TranslucentActivity"
 * android:theme="@style/TranslucentTheme">
 * <intent-filter>
 * <action android:name="android.intent.action.MAIN" />
 * <category android:name="android.intent.category.LAUNCHER" />
 * </intent-filter>
 * </activity>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-3-7.
 */
public class TranslucentActivity extends Activity {
    private static Class sClazz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, sClazz));
        finish();
    }

    /**
     * 设置要启动的Activity
     *
     * @param cls
     */
    public static void setStartActivity(Class<?> cls) {
        sClazz = cls;
    }
}

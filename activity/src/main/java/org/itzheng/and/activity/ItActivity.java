package org.itzheng.and.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import org.itzheng.and.activity.permissions.IPermissionManager;
import org.itzheng.and.activity.permissions.PermissionHelper;
import org.itzheng.and.activity.window.IWindowStatus;
import org.itzheng.and.activity.window.helper.WindowStatusHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-1.
 */
public class ItActivity extends AppCompatActivity implements IWindowStatus {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private IWindowStatus mWindowStatus;
    /**
     * 权限请求工具
     */
    private IPermissionManager permissionHelper;

    private IPermissionManager getPermissionHelper() {
        if (permissionHelper == null) {
            permissionHelper = new PermissionHelper(this);
        }
        return permissionHelper;
    }

    private IWindowStatus getWindowStatusHelper() {
        if (mWindowStatus == null) {
            mWindowStatus = WindowStatusHelper.newInstance(this);
        }
        return mWindowStatus;
    }

    @Override
    public void setFullScreen(boolean on) {
        getWindowStatusHelper().setFullScreen(on);
    }

    @Override
    public void setHideActionBar(boolean on) {
        getWindowStatusHelper().setHideActionBar(on);

    }

    @Override
    public void setTranslucentStatus(boolean on) {
        getWindowStatusHelper().setTranslucentStatus(on);
    }

    @Override
    public void setTranslucentStatus(boolean on, boolean isFullTranslucent) {
        getWindowStatusHelper().setTranslucentStatus(on, isFullTranslucent);
    }

    @Override
    public void setTranslucentNavigation(boolean on) {
        getWindowStatusHelper().setTranslucentNavigation(on);
    }

    @Override
    public void setHideNavigation(boolean on, boolean isSticky) {
        getWindowStatusHelper().setHideNavigation(on, isSticky);
    }

    @Override
    public void setHideNavigation(boolean on) {
        getWindowStatusHelper().setHideNavigation(on);
    }

    @Override
    public boolean isHideNavigation() {
        return getWindowStatusHelper().isHideNavigation();
    }

    @Override
    public void setNavigationBarColor(int color) {
        getWindowStatusHelper().setNavigationBarColor(color);
    }

    @Override
    public void setStatusBarDarkMode(boolean on) {
        getWindowStatusHelper().setStatusBarDarkMode(on);
    }

    @Override
    public void setStatusBarColor(int color) {
        getWindowStatusHelper().setStatusBarColor(color);
    }

    @Override
    public void setKeepScreenOn(boolean on) {
        getWindowStatusHelper().setKeepScreenOn(on);
    }

    /**
     * 请求某些权限，如果本身都具备权限了就直接通过，只有当没有权限才会使用请求功能、。
     *
     * @param listener    结果回调
     * @param permissions {@link Manifest.permission}
     */
    public void requestPermissions(IPermissionManager.OnRequestPermissionListener listener, String... permissions) {
        getPermissionHelper().requestPermissions(listener, permissions);
    }

    /**
     * 请求权限的回调，FragmentActivity 自带
     * 该方法应该会至少执行2次，第一次，请求的时候，@param permissions 返回空，第二次，@param permissions返回请求的权限
     *
     * @param requestCode  请求码，因为害怕每次执行不同，造成遗漏的回调，每次请求码递增
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        getPermissionHelper().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPermissionHelper().onDestroy();
    }
}

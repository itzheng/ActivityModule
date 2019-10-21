package org.itzheng.and.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.itzheng.and.activity.permissions.IPermissionManager;
import org.itzheng.and.activity.permissions.PermissionHelper;
import org.itzheng.and.activity.ui.keyboard.ISoftInput;
import org.itzheng.and.activity.ui.keyboard.ItSoftInput;
import org.itzheng.and.activity.ui.loading.ILoading;
import org.itzheng.and.activity.ui.loading.ItLoadingView;
import org.itzheng.and.activity.ui.toast.IToast;
import org.itzheng.and.activity.ui.toast.ItToast;
import org.itzheng.and.activity.window.IWindowStatus;
import org.itzheng.and.activity.window.helper.WindowStatusHelper;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-1.
 */
public class ItActivity extends AppCompatActivity implements IWindowStatus, IToast, ISoftInput, ILoading {
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
    public void finish() {
        if (iToast != null) {
            iToast.finish();
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPermissionHelper().onDestroy();
        iToast = null;
        recycleSoftInput();
    }
//===================================== Toast ================================
    /**
     * 使用Toast接口
     */
    protected IToast iToast;

    /**
     * 初始化Toast
     */
    protected synchronized void initToast() {
        if (iToast == null) {
            iToast = new ItToast(this);
        }
    }

    /**
     * 弹出 toast
     *
     * @param resId
     */
    @Override
    public void showToast(int resId) {
        initToast();
        if (iToast != null) {
            iToast.showToast(resId);
        }
    }

    @Override
    public void showToast(String string) {
        initToast();
        if (iToast != null) {
            iToast.showToast(string);
        }
    }

    /**
     * 方便 findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T getView(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * 跳转到 Activity
     *
     * @param clazz
     * @param <T>
     */
    public <T extends Activity> void startActivity(Class<T> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    //============================ SoftInput ==============================
    protected ISoftInput iSoftInput;

    /**
     * 初始化软键盘工具类
     */
    protected synchronized void initSoftInput() {
        if (iSoftInput == null) {
            iSoftInput = new ItSoftInput(this);
        }
    }

    /**
     * 回收软键盘工具类，避免内存泄漏
     */
    private synchronized void recycleSoftInput() {
        if (iSoftInput != null) {
            iSoftInput.setOnSoftInputChangedCallback(null);
            iSoftInput = null;
        }
    }

    @Override
    public void showSoftInput() {
        initSoftInput();
        if (iSoftInput != null) {
            iSoftInput.showSoftInput();
        }
    }


    @Override
    public void hideSoftInput() {
        initSoftInput();
        if (iSoftInput != null) {
            iSoftInput.hideSoftInput();
        }
    }

    @Override
    public boolean isSoftInputShowing() {
        initSoftInput();
        return iSoftInput != null && iSoftInput.isSoftInputShowing();
    }

    @Override
    public void setOnSoftInputChangedCallback(OnSoftInputChanged callback) {
        initSoftInput();
        if (iSoftInput != null) {
            iSoftInput.setOnSoftInputChangedCallback(callback);
        }
    }

    //====================================== Loading ================================
    protected ILoading iLoading;

    protected synchronized void initLoading() {
        if (iLoading == null) {
            //如果调用者想改自己的进度样式，只要在 layout 中写名为 it_layout_view_loading 的布局即可。
            iLoading = ItLoadingView.newInstance(this);
        }
    }

    @Override
    public void showLoading() {
        initLoading();
        if (iLoading != null) {
            iLoading.showLoading();
        }
    }

    @Override
    public void dismissLoading() {
        initLoading();
        if (iLoading != null) {
            iLoading.dismissLoading();
        }
    }

    @Override
    public boolean isLoading() {
        return iLoading != null && iLoading.isLoading();
    }

}

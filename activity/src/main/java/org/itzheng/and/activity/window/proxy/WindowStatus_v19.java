package org.itzheng.and.activity.window.proxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.itzheng.and.activity.window.IWindowStatus;

/**
 * Title:状态设置Api19或以上(19,20)<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-24.
 */
public class WindowStatus_v19 implements IWindowStatus {
    public static WindowStatus_v19 newInstance(Activity activity) {
        return new WindowStatus_v19(activity);
    }

    /**
     * 高版本的工具类只有功能需要低版本兼容设置，其他的使用高版本的方法就可以
     */
    private IWindowStatus mWindowStatus;
    private Activity mActivity;
    /**
     * 是否设置透明虚拟返回键
     */
    private boolean isTranslucentNavigation = false;
    /**
     * 是否设置全屏
     */
    private boolean isFullScreen = false;
    /**
     * 是否透明状态栏，如果设置透明虚拟导航栏，则状态栏默认也是透明的
     */
    private boolean isTranslucentStatus = false;

    /**
     * 默认的虚拟返回键颜色
     */
    private static final int DEF_NAVIGATION_COLOR = 0x000000;
    /**
     * 当前的虚拟返回键盘的颜色
     */
    private int mNavigationBarColor = DEF_NAVIGATION_COLOR;
    /**
     * 状态栏颜色
     */
    private int mStatusBarColor = -1;

    private WindowStatus_v19(Activity activity) {
        mActivity = activity;
        mWindowStatus = WindowStatus_v21.newInstance(activity);
    }

    @Override
    public void setFullScreen(boolean on) {
        isFullScreen = on;
        mWindowStatus.setFullScreen(on);
    }

    @Override
    public void setHideActionBar(boolean on) {
        mWindowStatus.setHideActionBar(on);
    }

    private static final String TAG = "WindowStatus_v19";

    @Override
    public void setTranslucentStatus(boolean on) {
        isTranslucentStatus = on;
        mWindowStatus.setTranslucentStatus(on);
        if (on) {
            removeStatusBar();
        } else {
            if (mStatusBarColor != -1 && mStatusBarColor != Color.TRANSPARENT) {
                setStatusBarColor(mStatusBarColor);
            }
        }
    }

    @Override
    public void setTranslucentNavigation(boolean on) {
        isTranslucentNavigation = on;
        mWindowStatus.setTranslucentNavigation(on);
        if (on) {
            removeNavigationBar();
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            resetNavigationBarColor();
        }
    }

    /**
     * 重新设置虚拟返回栏颜色
     */
    private void resetNavigationBarColor() {
        if (mNavigationBarColor != -1 && mNavigationBarColor != Color.TRANSPARENT) {
            setNavigationBarColor(mNavigationBarColor);
        }
    }

    @Override
    public void setHideNavigation(boolean on, boolean isSticky) {
        mWindowStatus.setHideNavigation(on, isSticky);
        if (on) {
            removeNavigationBar();
        } else {
            resetNavigationBarColor();
        }

    }

    @Override
    public void setHideNavigation(boolean on) {
        setHideNavigation(on, true);
    }

    @Override
    public boolean isHintNavigation() {
        return mWindowStatus.isHintNavigation();
    }

    @Override
    public void setNavigationBarColor(int color) {
        mNavigationBarColor = color;
        if (isTranslucentNavigation) {
            return;
        }
        if (isHintNavigation()) {
            //隐藏虚拟导航栏，有返回
            return;
        }
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        if (color == -1 || color == Color.TRANSPARENT) {
            removeNavigationBar();
            return;
        }
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View view = getNavigationBarView(mActivity);
        view.setBackgroundColor(color);
        if (view.getParent() == null) {
            decorView.addView(view);
        }
        resetContentPadding();
    }

    @Override
    public void setStatusBarDarkMode(boolean on) {
//        mWindowStatus.setStatusBarDarkMode(on);
        setStatusBarColor(Color.BLACK);
    }

    @Override
    public void setStatusBarColor(int color) {
        mStatusBarColor = color;
        if (isTranslucentStatus) {
            return;
        }
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        if (color == -1 || color == Color.TRANSPARENT) {
            removeStatusBar();
            return;
        }
        View statusBarView = getStatusBarView(mActivity);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        statusBarView.setBackgroundColor(color);
        if (statusBarView.getParent() == null) {
            decorView.addView(statusBarView);
        }
    }

    /**
     * 移除状态栏
     */
    private void removeStatusBar() {
        View statusBarView = getStatusBarView(mActivity);
        ViewParent viewParent = statusBarView.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(statusBarView);
        }
    }

    /**
     * 移除虚拟导航栏
     */
    private void removeNavigationBar() {
        Log.d(TAG, "removeNavigationBar");
        View view = getNavigationBarView(mActivity);
        ViewParent viewParent = view.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(view);
        } else {
            Log.d(TAG, "viewParent == null");
        }
        resetContentPadding();
    }

    @Override
    public void setKeepScreenOn(boolean on) {
        mWindowStatus.setKeepScreenOn(on);
    }

    private View getContentView() {
        return mActivity.findViewById(android.R.id.content);
    }

    /**
     * 状态改变，重新设置内容的padding，4.4 机型适配方案
     */
    private void resetContentPadding() {
        View contentView = getContentView();
        int paddingTop = 0, paddingBottom = 0;
        if (isTranslucentNavigation || isHintNavigation()) {
            paddingBottom = 0;
        } else {
            paddingBottom = getNavigationBarHeight(mActivity);
        }
//        if (!isTranslucentNavigation) {
//            paddingBottom = getNavigationBarHeight(mActivity);
//            if (!isTranslucentStatus && !isFullScreen) {
//                paddingTop = getStatusBarHeight(mActivity);
//            }
//        }
        getContentView().setPadding(contentView.getPaddingLeft(), paddingTop, contentView.getPaddingRight(), paddingBottom);
    }
//
//    /**
//     * 是否已经设置透明虚拟返回栏
//     *
//     * @return
//     */
//    private boolean isTranslucentNavigation() {
//        Window win = mActivity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//        return (winParams.flags | bits) == winParams.flags;
//    }

    /**
     * 4.4 -- 5.0 的状态栏
     */
    private View mStatusBarView;

    /**
     * 获取状态栏 4.4 -- 5.0 的状态栏
     *
     * @param activity
     * @return
     */
    private View getStatusBarView(Activity activity) {
        if (mStatusBarView == null) {
            Log.d(TAG, "mStatusBarView == null");
            mStatusBarView = new View(activity);
            FrameLayout.LayoutParams params;
            params = new FrameLayout.
                    LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(mActivity));
            params.gravity = Gravity.TOP;
            mStatusBarView.setLayoutParams(params);
        }
        return mStatusBarView;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    protected int getStatusBarHeight(Context context) {
        int height = 0;
        int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (id > 0) {
            height = context.getResources().getDimensionPixelSize(id);
        }
        return height;
    }

    /**
     * 4.4 以下的虚拟导航栏
     */
    private View mNavigationBarView;

    /**
     * 创建一个虚拟导航栏
     *
     * @param activity
     * @return
     */
    private View getNavigationBarView(Activity activity) {
        if (mNavigationBarView == null) {
            mNavigationBarView = new View(activity);
            FrameLayout.LayoutParams params;
            params = new FrameLayout.
                    LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getNavigationBarHeight(mActivity));
            params.gravity = Gravity.BOTTOM;
            mNavigationBarView.setLayoutParams(params);
        }
        return mNavigationBarView;
    }

    /**
     * 获取虚拟导航栏的高度
     *
     * @param context
     * @return
     */
    protected int getNavigationBarHeight(Context context) {
        int height = 0;
        int id = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0) {
            height = context.getResources().getDimensionPixelSize(id);
        }
        return height;
    }

}

package org.itzheng.and.activity.window.proxy;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.itzheng.and.activity.window.IWindowStatus;

/**
 * Title:状态设置23或以上<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-24.
 */
public class WindowStatus_v23 implements IWindowStatus {
    public static IWindowStatus newInstance(Activity activity) {
        IWindowStatus ins = new WindowStatus_v23(activity);
        return ins;
    }

    private Activity mActivity;

    private WindowStatus_v23(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void setFullScreen(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setHideActionBar(boolean on) {
        if (mActivity instanceof AppCompatActivity) {
            android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (actionBar != null) {
                if (on) {
                    actionBar.hide();
                } else {
                    actionBar.show();
                }
            }
        } else {
            ActionBar actionBar = mActivity.getActionBar();
            if (actionBar != null) {
                if (on) {
                    actionBar.hide();
                } else {
                    actionBar.show();
                }
            }
        }
    }

    private static final String TAG = "WindowStatus_v23";

    @Override
    public void setTranslucentStatus(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    @Override
    public void setTranslucentNavigation(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setHideNavigation(boolean on, boolean isSticky) {
        View decorView = mActivity.getWindow().getDecorView();
        int flags = decorView.getSystemUiVisibility();
        int bits = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (on) {
            flags |= bits;
            int sticky = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (isSticky) {
                flags |= sticky;
            } else {
                flags &= ~sticky;
            }
        } else {
            flags &= ~bits;
        }
        // Set the new desired visibility.
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    public void setHideNavigation(boolean on) {
        setHideNavigation(on, true);
    }

    @Override
    public boolean isHintNavigation() {
        int bits = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        View decorView = mActivity.getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        return (systemUiVisibility | bits) == systemUiVisibility;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setNavigationBarColor(int color) {
        mActivity.getWindow().setNavigationBarColor(color);
    }

//    @Override
//    public void setOnSystemUiVisibilityChangeListener(final View.OnSystemUiVisibilityChangeListener l) {
//        mActivity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if (l != null) {
//                    l.onSystemUiVisibilityChange(visibility);
//                }
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setStatusBarDarkMode(boolean on) {
        //6.0 以上的
        View decorView = mActivity.getWindow().getDecorView();
        int flags = decorView.getSystemUiVisibility();
        int bits = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        if (on) {
            flags |= bits;
        } else {
            flags &= ~bits;
        }
        decorView.setSystemUiVisibility(flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBarColor(int color) {
        mActivity.getWindow().setStatusBarColor(color);
    }

    /**
     * 保持屏幕常亮
     *
     * @param on
     */
    public void setKeepScreenOn(boolean on) {
        if (on) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}

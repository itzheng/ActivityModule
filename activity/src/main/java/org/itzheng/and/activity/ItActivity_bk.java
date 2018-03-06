package org.itzheng.and.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-1.
 */
public class ItActivity_bk extends AppCompatActivity {
    private boolean isFullScreen = false;

    /**
     * 全屏，即隐藏系统状态栏
     * 此时，看不见时间等系统图标，但是并没有隐藏底部的虚拟按键
     *
     * @param on
     */
    protected void setFullScreen(boolean on) {
        isFullScreen = on;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        resetContentPadding();
    }

    /**
     * 隐藏系统标题栏
     *
     * @param on
     */
    protected void setHideActionBar(boolean on) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (on) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }

    }

    /**
     * 是否透明状态栏，如果设置透明虚拟导航栏，则状态栏默认也是透明的
     */
    private boolean isTranslucentStatus = false;

    /**
     * 设置透明状态栏，
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        isTranslucentStatus = on;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        resetContentPadding();
    }

    /**
     * 是否设置透明虚拟返回键
     */
    private boolean isTranslucentNavigation = false;

    /**
     * 设置底部虚拟返回键为透明
     * true 虚拟返回键透明，并且布局沉浸到虚拟返回键上，如果此时没有设置状态栏透明，那么布局将自动沉浸到状态栏，只是被顶层颜色覆盖
     * false 布局没有沉浸到虚拟返回键上
     *
     * @param on
     */
    protected void setTranslucentNavigation(boolean on) {
        isTranslucentNavigation = on;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        //4.4--5.0,因为手动添加虚拟导航栏，所以需要去掉
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (on) {
                View navigationBarView = getNavigationBarView(this);
                if (navigationBarView.getParent() != null) {
                    ((ViewGroup) navigationBarView.getParent()).removeView(navigationBarView);
                }
                resetContentPadding();
            } else {
                setNavigationBarColor(mNavigationBarColor);
            }
        }

    }

    /**
     * 隐藏底部虚拟返回键,如果触摸到屏幕会自动显示返回键
     *
     * @param on
     */
    protected void setHintNavigation(boolean on) {
        int bits = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        View decorView = getWindow().getDecorView();
        int flags = decorView.getSystemUiVisibility();
        if (on) {
            flags |= bits;
        } else {
            flags &= ~bits;
        }
        decorView.setSystemUiVisibility(flags);
    }

    /**
     * 当前是否隐藏虚拟返回键
     *
     * @return
     */
    protected boolean isHintNavigation() {
        int bits = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        return (systemUiVisibility | bits) == systemUiVisibility;
    }

    /**
     * 默认的虚拟返回键颜色
     */
    private static final int DEF_NAVIGATION_COLOR = 0x000000;
    /**
     * 当前的虚拟返回键盘的颜色
     */
    private int mNavigationBarColor = DEF_NAVIGATION_COLOR;

    /**
     * 设置底部虚拟导航栏的颜色,4.4设置沉浸虚拟导航行栏，会自动设置透明状态栏
     *
     * @param color
     */
    public void setNavigationBarColor(int color) {
        mNavigationBarColor = color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上可以直接设置 navigation颜色
            getWindow().setNavigationBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            resetContentPadding();
            if (isTranslucentNavigation) {
                //如果是透明虚拟导航栏则不设置颜色
                //并且，不设置paddingbottom
//                View contentView = getContentView();
//                getContentView().setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), 0);
                return;
            }
            // android:clipToPadding="false"
            //android:fitsSystemWindows="true"
            //如果设置透明flag，布局会自动移动到虚拟按钮里面
            //如果不设置透明flag，4.4无效

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置padding底部就可以
//            View contentView = getContentView();
//            getContentView().setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), getNavigationBarHeight(this));

            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View mNavigationBar = getNavigationBarView(this);
            mNavigationBar.setBackgroundColor(color);
            if (mNavigationBar.getParent() == null) {
                FrameLayout.LayoutParams params;
                params = new FrameLayout.
                        LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getNavigationBarHeight(this));
                params.gravity = Gravity.BOTTOM;
                mNavigationBar.setLayoutParams(params);
                decorView.addView(mNavigationBar);
            }
        } else {
            //4.4以下无法设置navigationbar颜色
        }
    }

    private View getContentView() {
        if (true) {
            return null;
        }
        return this.findViewById(android.R.id.content);
    }

    /**
     * Ui界面改变的监听
     *
     * @param l
     */
    protected void setOnSystemUiVisibilityChangeListener(final View.OnSystemUiVisibilityChangeListener l) {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (l != null) {
                    l.onSystemUiVisibilityChange(visibility);
                }
            }
        });
    }

    private static final String TAG = "ItActivity";

    /**
     * 状态改变，重新设置内容的padding，4.4 机型适配方案
     */
    private void resetContentPadding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (!isTranslucentNavigation()) {
            return;
        }
        View contentView = getContentView();
        int paddingTop = 0, paddingBottom = 0;
        if (!isTranslucentNavigation) {
            paddingBottom = getNavigationBarHeight(this);
            if (!isTranslucentStatus && !isFullScreen) {
                paddingTop = getStatusBarHeight(this);
            }
        }
        getContentView().setPadding(contentView.getPaddingLeft(), paddingTop, contentView.getPaddingRight(), paddingBottom);
    }

    /**
     * 是否已经设置透明虚拟返回栏
     *
     * @return
     */
    private boolean isTranslucentNavigation() {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        return (winParams.flags | bits) == winParams.flags;
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

    /**
     * 设置状态栏暗色（仅在6.0以上有效，6.0以下根据实际需要进行设置状态栏颜色）
     * true 时间等图标为黑色
     * false 时间等图标为亮色
     *
     * @param on
     */
    public void setStatusBarDarkMode(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0 以上的
            View decorView = getWindow().getDecorView();
            int flags = decorView.getSystemUiVisibility();
            int bits = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (on) {
                flags |= bits;
            } else {
                flags &= ~bits;
            }
            decorView.setSystemUiVisibility(flags);
        } else {
            //23以下，加个阴影背景，根据实际需要去重写

        }

    }

    /**
     * 设置状态栏颜色 最低支持版本 4.4
     *
     * @param color
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上可以直接设置 navigation颜色
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // android:clipToPadding="false"
            //android:fitsSystemWindows="true"
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = getStatusBarView(this);
            statusBarView.setBackgroundColor(color);
            if (statusBarView.getParent() == null) {
                FrameLayout.LayoutParams params;
                params = new FrameLayout.
                        LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(this));
                params.gravity = Gravity.TOP;
                statusBarView.setLayoutParams(params);
                decorView.addView(statusBarView);
            }
        } else {
            //4.4以下无法设置navigationbar颜色
        }
    }

    /**
     * 4.4 -- 5.0 的状态栏
     */
    View mStatusBarView;

    /**
     * 获取状态栏 4.4 -- 5.0 的状态栏
     *
     * @param activity
     * @return
     */
    private View getStatusBarView(Activity activity) {
        if (mStatusBarView == null) {
            mStatusBarView = new View(activity);
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
}

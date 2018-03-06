package org.itzheng.and.activity.window;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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
 * Created by itzheng on 2018-2-24.
 */
public interface IWindowStatus {
    /**
     * 全屏，即隐藏系统状态栏
     * 此时，看不见时间等系统图标，但是并没有隐藏底部的虚拟按键
     *
     * @param on
     */
    void setFullScreen(boolean on);

    /**
     * 隐藏系统标题栏
     *
     * @param on
     */
    void setHideActionBar(boolean on);

    /**
     * 设置透明状态栏，
     *
     * @param on
     */
    void setTranslucentStatus(boolean on);

    /**
     * 设置底部虚拟返回键为透明
     * true 虚拟返回键透明，并且布局沉浸到虚拟返回键上，如果此时没有设置状态栏透明，那么布局将自动沉浸到状态栏，只是被顶层颜色覆盖
     * false 布局没有沉浸到虚拟返回键上
     *
     * @param on
     */
    void setTranslucentNavigation(boolean on);

    /**
     * 隐藏底部虚拟返回键,如果触摸到屏幕会自动显示返回键
     *
     * @param on
     * @param isSticky 是否是粘性
     *                 true：点击屏幕不会显示虚拟导航栏，
     *                 false ：点击屏幕会自动显示虚拟导航栏相当于再次设置on为false
     */
    void setHideNavigation(boolean on, boolean isSticky);

    /**
     * 隐藏底部虚拟返回键,如果触摸到屏幕会自动显示返回键
     *
     * @param on
     */
    void setHideNavigation(boolean on);

    /**
     * 当前是否隐藏虚拟返回键
     *
     * @return
     */
    boolean isHintNavigation();

    /**
     * 设置底部虚拟导航栏的颜色,4.4设置沉浸虚拟导航行栏，会自动设置透明状态栏
     *
     * @param color
     */
    void setNavigationBarColor(int color);


//    /**
//     * Ui界面改变的监听
//     *
//     * @param l
//     */
//    void setOnSystemUiVisibilityChangeListener(final View.OnSystemUiVisibilityChangeListener l);


    /**
     * 设置状态栏暗色（仅在6.0以上有效，6.0以下根据实际需要进行设置状态栏颜色）
     * true 时间等图标为黑色
     * false 时间等图标为亮色
     *
     * @param on
     */
    void setStatusBarDarkMode(boolean on);

    /**
     * 设置状态栏颜色 最低支持版本 4.4
     *
     * @param color
     */
    void setStatusBarColor(int color);

    /**
     * 保持屏幕常亮
     *
     * @param on
     */
    void setKeepScreenOn(boolean on);

}

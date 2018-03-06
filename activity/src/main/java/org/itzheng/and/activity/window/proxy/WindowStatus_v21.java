package org.itzheng.and.activity.window.proxy;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import org.itzheng.and.activity.window.IWindowStatus;

/**
 * Title:状态设置21或以上（21,22）<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-24.
 */
public class WindowStatus_v21 implements IWindowStatus {
    public static WindowStatus_v21 newInstance(Activity activity) {
        return new WindowStatus_v21(activity);
    }

    /**
     * 高版本的工具类只有功能需要低版本兼容设置，其他的使用高版本的方法就可以
     */
    private IWindowStatus mWindowStatus;

    private WindowStatus_v21(Activity activity) {
        mWindowStatus = WindowStatus_v23.newInstance(activity);
    }

    @Override
    public void setFullScreen(boolean on) {
        mWindowStatus.setFullScreen(on);
    }

    @Override
    public void setHideActionBar(boolean on) {
        mWindowStatus.setHideActionBar(on);
    }

    @Override
    public void setTranslucentStatus(boolean on) {
        mWindowStatus.setTranslucentStatus(on);
    }

    @Override
    public void setTranslucentNavigation(boolean on) {
        mWindowStatus.setTranslucentNavigation(on);
    }

    @Override
    public void setHideNavigation(boolean on, boolean isSticky) {
        mWindowStatus.setHideNavigation(on, isSticky);
    }

    @Override
    public void setHideNavigation(boolean on) {
        mWindowStatus.setHideNavigation(on);
    }

    @Override
    public boolean isHintNavigation() {
        return mWindowStatus.isHintNavigation();
    }

    @Override
    public void setNavigationBarColor(int color) {
        mWindowStatus.setNavigationBarColor(color);
    }

    @Override
    public void setStatusBarDarkMode(boolean on) {
        // mWindowStatus.setStatusBarDarkMode(on);
// 加个阴影
        if (on) {
            setStatusBarColor(Color.BLACK);
        }
    }

    @Override
    public void setStatusBarColor(int color) {
        mWindowStatus.setStatusBarColor(color);
    }

    @Override
    public void setKeepScreenOn(boolean on) {
        mWindowStatus.setKeepScreenOn(on);
    }
}

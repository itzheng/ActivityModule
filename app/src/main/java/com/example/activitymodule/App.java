package com.example.activitymodule;

import android.app.Application;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-21.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initSwipeBack();
    }

    private void initSwipeBack() {
        BGASwipeBackHelper.init(this, null);
    }
}

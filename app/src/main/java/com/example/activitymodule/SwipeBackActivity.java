package com.example.activitymodule;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.itzheng.and.activity.ItSwipeBackActivity;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-21.
 */
public class SwipeBackActivity extends ItSwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(true);
        setContentView(R.layout.activity_swipe_back);
    }
}

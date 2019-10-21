package com.example.activitymodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.itzheng.and.activity.ItSwipeBackActivity;
import org.itzheng.and.activity.ui.loading.ILoading;
import org.itzheng.and.activity.ui.loading.ItLoadingView;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-21.
 */
public class LoadingActivity extends ItSwipeBackActivity implements ILoading {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getView(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
            }
        });
        getView(R.id.btnDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLoading();
            }
        });
        getView(R.id.btnIsLoading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("is loading " + isLoading());
            }
        });
    }

//    ILoading iLoading;

    @Override
    protected synchronized void initLoading() {
        if (iLoading == null) {
            //如果调用者想改自己的进度样式，只要在 layout 中写名为 it_layout_view_loading 的布局即可。
            iLoading = ItLoadingView.newInstance(this, getView(R.id.layoutContent));
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        setSwipeBackEnable(false);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        setSwipeBackEnable(true);
    }

    @Override
    public void onBackPressed() {
        if (isLoading()) {
            dismissLoading();
            showToast("正在加载中，不能关闭");
            return;
        }
        super.onBackPressed();
    }
}

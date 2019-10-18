package com.example.activitymodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.itzheng.and.activity.ItActivity;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public class KeyboardActivity extends ItActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        initClick();
        setOnSoftInputChangedCallback(new OnSoftInputChanged() {
            @Override
            public void onSoftInputShow() {
                showToast("键盘弹出");
            }

            @Override
            public void onSoftInputHide() {
                showToast("键盘隐藏");
            }
        });
    }

    private void initClick() {
        getView(R.id.btnOpen).setOnClickListener(this);
        getView(R.id.btnIsOpen).setOnClickListener(this);
        getView(R.id.btnClose).setOnClickListener(this);
        getView(R.id.btnJump).setOnClickListener(this);
        getView(R.id.btnBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpen:
                showSoftInput();
                break;
            case R.id.btnClose:
                hideSoftInput();
                break;
            case R.id.btnIsOpen:
                showToast("" + isSoftInputShowing());
                break;
            case R.id.btnJump:
                startActivity(KeyboardActivity.class);
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}

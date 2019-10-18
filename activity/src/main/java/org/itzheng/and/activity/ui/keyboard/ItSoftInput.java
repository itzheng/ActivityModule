package org.itzheng.and.activity.ui.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public class ItSoftInput implements ISoftInput {
    private Activity mActivity;
    private static final String TAG = "ItSoftInput";

    public ItSoftInput(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void showSoftInput() {
        if (mActivity == null) {
            Log.w(TAG, "showSoftKeyboard: " + "Activity is null");
            return;
        }
        View currentFocus = mActivity.getCurrentFocus();
        if (currentFocus != null
                && currentFocus.getWindowToken() != null) {
            currentFocus.requestFocus();
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showSoftInput(currentFocus,
                            InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void hideSoftInput() {
        View currentFocus = mActivity.getCurrentFocus();
        if (currentFocus != null
                && currentFocus.getWindowToken() != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(currentFocus.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean isSoftInputShowing() {
        if (mActivity == null) {
            Log.w(TAG, "isSoftInputShowing: " + "Activity is null");
            return false;
        }
        //这个算法是监听键盘改变的算法，如果界面未初始化，应该是无效的，其他情况应该没问题
        View v = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        Rect r = new Rect();
        v.getWindowVisibleDisplayFrame(r);
        int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
        return heightDiff > dp2px(v.getContext(), 100);
    }

    /**
     * dp转换工具
     *
     * @param context
     * @param dpValue
     * @return
     */

    private static int dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private boolean keyboardVisible;
    /**
     * 键盘弹出的布局监听
     */
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;

    @Override
    public void setOnSoftInputChangedCallback(final OnSoftInputChanged callback) {
        if (mActivity == null) {
            Log.w(TAG, "isSoftInputShowing: " + "Activity is null");
            return;
        }
        if (callback == null) {
            //回收
            recycle();
            return;
        }
        final View v = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (v == null) {
            return;
        }
        keyboardVisible = isSoftInputShowing();
        //高度应该是用dp，不同机型高度不一样
        final int SOFT_KEY_BOARD_MIN_HEIGHT = dp2px(v.getContext(), 100);
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);
                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than 100 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        callback.onSoftInputShow();
//                        onShowKeyboard(heightDiff);
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        callback.onSoftInputHide();
                    }
                }
            }
        };
        v.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    /**
     * 回收监听
     */
    private void recycle() {
        if (mOnGlobalLayoutListener != null) {
            final View v = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
            if (v != null) {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
            }
            mOnGlobalLayoutListener = null;
        }

    }
}

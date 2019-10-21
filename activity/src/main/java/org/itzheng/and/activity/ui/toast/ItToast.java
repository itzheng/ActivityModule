package org.itzheng.and.activity.ui.toast;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public class ItToast implements IToast {
    public ItToast(Activity activity) {
        mContext = activity;
    }

    private Activity mContext;
    ActivityToastPopup toastPopup;

    @Override
    public void showToast(int resId) {
        showToast(mContext.getString(resId));
    }

    /**
     * 显示toast，如果系统默认的能使用则使用系统默认的，如果不能使用则调用自定义
     *
     * @param string
     */
    @Override
    public void showToast(String string) {
        //创建Popup必须Activity渲染完毕才行
        if (!mContext.hasWindowFocus() || ActivityToastPopup.isSystemToast(mContext)) {
            //默认使用系统Toast
            SystemToast.showToast(mContext, string);
            return;
        }
        if (toastPopup == null) {
            toastPopup = new ActivityToastPopup(mContext);
        }
        toastPopup.makeText(string);
        if (!toastPopup.isShowing()) {
            runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mContext != null && !mContext.isFinishing()) {
                        //如果finish就不用显示了，不然会出出现Activity内存泄漏
                        toastPopup.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, dip2px(mContext, 64)
                                + getNavigationBarHeight(mContext));
                    }
                }
            }, 0);
        }
    }

    @Override
    public void finish() {
        if (toastPopup != null && toastPopup.isShowing()) {
            toastPopup.dismiss();
        }
    }

    /**
     * 在UI线程中执行
     *
     * @param command
     */
    public static void runOnUiThreadDelayed(final Runnable command, final long delayMillis) {
        if (Looper.myLooper() == null)
            Looper.prepare();
        new Handler(Looper.getMainLooper()).postDelayed(command, delayMillis);
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 获取导航栏的高度，如果有没有导航栏则返回0
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        if (!isNavigationBarShow(activity)) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static boolean isNavigationBarShow(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    private static class SystemToast {
        /**
         * 单例吐司
         */
        private static Toast mToast;
        private static final Object SYNC_LOCK = new Object();


        public static void showToast(Context context, String string) {
            if (mToast == null) {
                synchronized (SYNC_LOCK) {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                    }
                }
            } else {
                mToast.cancel();//将之前的取消，如果不取消，连续点击10次后会导致部分点击不弹
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setText(string);
            }
            //延时执行，是因为刚刚取消，如果马上执行会不生效
            runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mToast != null)
                        mToast.show();
                }
            }, 5);
        }
    }
}

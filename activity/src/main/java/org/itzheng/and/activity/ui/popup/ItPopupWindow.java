package org.itzheng.and.activity.ui.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2017/8/4.
 */
public class ItPopupWindow extends PopupWindow {
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public ItPopupWindow(Context context) {
        super(context);
        mContext = context;
        //当用户有底部虚拟导航栏的时候，自动距离底部虚拟导航栏
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
//        setBackgroundDrawable(new ColorDrawable(UIUtils.getColor(R.color.colorBlack_5p)));
        setOutsideTouchable(true);
    }

    private boolean mCancelable = false;
    private OnCancelListener mOnCancelListener;

    public void setCancelable(boolean cancelable) {
        mCancelable = cancelable;
    }

    public void setOnCancelListener(OnCancelListener listener) {
        mOnCancelListener = listener;
    }

    /**
     * 是否拦截返回键，需要外界主动调用判断，已经在初始化时put了
     * setFocusable(true) 可以直接在Activity中拦截onBackPressed
     *
     * @return
     */
    public boolean onBackPressed() {
        if (isShowing()) {
            //如果正在显示，则拦截事件。如果可以取消则取消
            if (mCancelable) {
                if (mOnCancelListener != null) {
                    mOnCancelListener.onCancel(this);
                }
                dismiss();
            }
            return true;
        }
        return false;
    }

    /**
     * 取消的监听，用户按返回键，如果可以返回的话，执行
     */
    public interface OnCancelListener {
        void onCancel(PopupWindow basePopupWindow);
    }

    /**
     * 覆盖状态栏
     * https://blog.csdn.net/qq_25238883/article/details/93379426
     *
     * @param mPopupWindow
     * @param needFullScreen
     */
    public static void fitPopupWindowOverStatusBar(PopupWindow mPopupWindow, boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(needFullScreen);
                mLayoutInScreen.set(mPopupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}

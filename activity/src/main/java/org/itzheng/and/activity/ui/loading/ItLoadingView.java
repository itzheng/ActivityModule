package org.itzheng.and.activity.ui.loading;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.itzheng.and.activity.R;

/**
 * Title:自己实现的加载进度条<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-4-16.
 */
public class ItLoadingView extends FrameLayout implements ILoading {
    /**
     * 使用View实例化，最终将显示到最近的 FrameLayout 上
     *
     * @param view
     * @return
     */
    public static ILoading newInstance(Context context, View view) {
        ItLoadingView iLoading = new ItLoadingView(context);
        iLoading.setAlpha(0);
        iLoading.setAttachView(view);
//        iLoading.attachToRoot(iLoading.getAttachView(view));
        return iLoading;
    }

    private void setAttachView(View view) {
        attachView = getAttachView(view);
    }

    /**
     * 使用 Activity 实例化，最终将显示到 Activity 最外层
     *
     * @param activity
     * @return
     */
    public static ILoading newInstance(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            //如果Activity结束就不创建了
            return null;
        }
        return newInstance(activity, (ViewGroup) activity.getWindow().getDecorView());
    }

    public ItLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public ItLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        attachView = (ViewGroup) getParent();
    }

    private void init() {
        //加载布局为填充父窗体，即铺满整个面
        ViewGroup.LayoutParams plp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(plp);
        setClickable(true);
        LayoutInflater.from(getContext()).inflate(R.layout.it_layout_view_loading, this, true);
    }

    private static final String TAG = "ItLoadingView";

    @Override
    public void showLoading() {
        if (getParent() == null) {
            if (attachView != null) {
                attachToRoot((ViewGroup) attachView);
            } else if (getContext() instanceof Activity) {
                Log.w(TAG, "attachView is null");
                ViewGroup viewParent = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
                attachToRoot(viewParent);
            }
        }
        //应该慢慢从透明度 0 到 1
        alphaAnimation(1, null);
        setVisibility(VISIBLE);

    }

    @Override
    public void dismissLoading() {
        //应该先透明度到0，然后再消失
        alphaAnimation(0, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
                //不显示就移除
                ViewGroup viewGroup = (ViewGroup) getParent();
                if (viewGroup != null)
                    viewGroup.removeView(ItLoadingView.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    ObjectAnimator objectAnimator;

    /**
     * 从当前透明度到指定透明度
     *
     * @param to       指定透明度
     * @param listener
     */
    private void alphaAnimation(int to, Animator.AnimatorListener listener) {
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            objectAnimator = null;
        }
        //0-1 = 2000;
        float alpha = getAlpha();
        int duration = Math.abs((int) ((alpha - to) * 200));
        objectAnimator = ObjectAnimator.ofFloat(this, "alpha", alpha, to);
        objectAnimator.removeAllListeners();
        if (listener != null) {
            objectAnimator.addListener(listener);
        }
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    @Override
    public boolean isLoading() {
        return attachView != null && getVisibility() == VISIBLE;
    }

    private ViewGroup attachView;

    /**
     * 将view添加到ViewGroup
     *
     * @param viewParent
     */
    private void attachToRoot(ViewGroup viewParent) {
        if (viewParent == null) {
            return;
        }
        attachView = viewParent;
        //判断是否已经有父窗体，如果有，则移除
        if (getParent() != null) ((ViewGroup) getParent()).removeView(this);
        //设置宽高为填充父窗体
        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        viewParent.addView(this, params);
    }


    /**
     * 填充到哪个view
     *
     * @return
     */
    private ViewGroup getAttachView(View view) {
        while (true) {
            if (view == null) {
                Log.w(TAG, "getAttachView " + "view is null");
                return null;
            } else if (view instanceof FrameLayout || view instanceof RelativeLayout || view instanceof ViewPager) {
                return (ViewGroup) view;
            }
            view = (View) view.getParent();
        }
    }
}

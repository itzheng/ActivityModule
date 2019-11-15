package org.itzheng.and.activity.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-11-13.
 */
public abstract class AttachView extends FrameLayout {
    private static final String TAG = "AttachView";

    public AttachView(@NonNull Context context) {
        this(context, null);
    }

    public AttachView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttachView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.it_layout_view_empty, this, true);
        View view = onCreateView();
        removeAllViews();
        addView(view);
    }

    protected abstract View onCreateView();

    public void showAtView(ViewGroup viewParent) {
        attachToRoot(viewParent);
        //应该慢慢从透明度 0 到 1
        alphaAnimation(1, null);
        setVisibility(VISIBLE);
    }

    public void show() {
        if (getParent() == null) {
            if (attachView != null) {
                showAtView((ViewGroup) attachView);
            } else if (getContext() instanceof Activity) {
                Log.w(TAG, "attachView is null");
                ViewGroup viewParent = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
                showAtView(viewParent);
            }
        }
    }

    public void dismiss() {
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
                    viewGroup.removeView(getThis());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private View getThis() {
        return this;
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

    public boolean isShowing() {
        return attachView != null && getVisibility() == VISIBLE;
    }

    protected void setAttachView(View view) {
        attachView = getAttachView(view);
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
            } else if (view instanceof NestedScrollView || view instanceof ScrollView) {
                //这些是属于 FrameLayout ，但是，只能有一个子内容，所以不能用
            } else if (view instanceof FrameLayout || view instanceof RelativeLayout || view instanceof ViewPager) {
                return (ViewGroup) view;
            }
            view = (View) view.getParent();
        }
    }
}

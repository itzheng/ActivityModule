package org.itzheng.and.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Title:侧滑返回，主要用于需要侧滑返回的界面<br>
 * Description: <br>
 * https://github.com/bingoogolapple/BGASwipeBackLayout-Android
 * <p>
 * 需要在 build.gradle 添加依赖
 * implementation 'cn.bingoogolapple:bga-swipebacklayout:1.2.0@aar'
 * <p>
 * 在 Application 的 oncreate 初始化
 * BGASwipeBackHelper.init(this, null);
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-21.
 */
public class ItSwipeBackActivity extends ItActivity implements BGASwipeBackHelper.Delegate {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isSupportSwipeBack()) {
            initSwipeBackFinish();
        }
        super.onCreate(savedInstanceState);
    }

    protected BGASwipeBackHelper mSwipeBackHelper;

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    protected void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持侧滑关闭
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    /**
     * 设置滑动返回是否可用。
     *
     * @param swipeBackEnable
     * @return
     */
    public void setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.setSwipeBackEnable(swipeBackEnable);
        }
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper != null && mSwipeBackHelper.isSliding()) {
            //正在滑动的时候取消返回事件
            return;
        }
        if (isSupportSwipeBack()) {
            //如果支持滑动关闭则使用插件回调
            mSwipeBackHelper.backward();
        } else {
            //不支持滑动关闭使用系统返回
            super.onBackPressed();
        }

    }

}

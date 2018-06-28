package org.itzheng.and.activity.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.itzheng.and.activity.R;


/**
 * 使用方法：
 * 在base Activity Finish 中加入：finishDefaultAnim
 * {
 * super.finish();
 * ActivityAnimUtil.finishDefaultAnim(this);
 * }
 * 在start Activity时，可以直接ActivityAnimUtil.startActivity
 */
public class ActivityAnimUtil {
    /**
     * 打开Activity的进入动画
     */
    public static final String BUNDLE_KEY_INT_ACTIVITY_START_ENTER_ANIM = "START_ENTER_ANIM";
    /**
     * 打开Activity的退出动画
     */
    public static final String BUNDLE_KEY__INT_ACTIVITY_START_EXIT_ANIM = "START_EXIT_ANIM";
    /**
     * 关闭Activity的进入动画
     */
    public static final String BUNDLE_KEY_INT_ACTIVITY_FINISH_ENTER_ANIM = "FINISH_ENTER_ANIM";
    /**
     * 关闭Activity的退出动画
     */
    public static final String BUNDLE_KEY__INT_ACTIVITY_FINISH_EXIT_ANIM = "FINISH_EXIT_ANIM";

    /**
     * 启动一个Activity，其他的都基于这个封装或者修改
     *
     * @param act
     * @param intent          要跳转的界面
     * @param startEnterAnim  启动的进入动画
     * @param startExitAnim   启动的结束动画
     * @param finishEnterAnim 退出的进入动画
     * @param finishExitAnim  退出的结束动画
     */
    public static void startActivity(Activity act, Intent intent, int startEnterAnim, int startExitAnim, int finishEnterAnim, int finishExitAnim) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(BUNDLE_KEY_INT_ACTIVITY_START_ENTER_ANIM, startEnterAnim);
        bundle.putInt(BUNDLE_KEY__INT_ACTIVITY_START_EXIT_ANIM, startExitAnim);
        bundle.putInt(BUNDLE_KEY_INT_ACTIVITY_FINISH_ENTER_ANIM, finishEnterAnim);
        bundle.putInt(BUNDLE_KEY__INT_ACTIVITY_FINISH_EXIT_ANIM, finishExitAnim);
        intent.putExtras(bundle);
        act.startActivity(intent);
        act.overridePendingTransition(startEnterAnim, startExitAnim);
    }

    /**
     * 启动一个Activity，其他的都基于这个封装或者修改
     *
     * @param act
     * @param activityClazz   要跳转的界面
     * @param startEnterAnim  启动的进入动画
     * @param startExitAnim   启动的结束动画
     * @param finishEnterAnim 退出的进入动画
     * @param finishExitAnim  退出的结束动画
     */
    public static void startActivity(Activity act, Class<? extends Activity> activityClazz,
                                     int startEnterAnim, int startExitAnim, int finishEnterAnim, int finishExitAnim) {
        startActivity(act, new Intent(act, activityClazz), startEnterAnim, startExitAnim, finishEnterAnim, finishExitAnim);
    }

    public static void startActivity(Activity activity, Class<? extends Activity> activityClazz) {
        startActivity(activity, new Intent(activity, activityClazz));
    }

    public static void startActivity(Activity activity, Intent intent) {
        startActivityRightInRightOut(activity, intent);
    }

    public static void startActivityRightInRightOut(Activity activity, Intent intent) {
        startActivity(activity, intent, R.anim.push_right_in, R.anim.push_right_out,
                R.anim.pull_right_in, R.anim.pull_right_out);

    }

    public static void startActivityLeftInLeftOut(Activity activity, Intent intent) {
        startActivity(activity, intent, android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    public static void startActivityLeftInLeftOut(Activity activity, Class<? extends Activity> clazz) {
        startActivityLeftInLeftOut(activity, getIntent(activity, clazz));

    }

    /**
     * 右进右出
     *
     * @param activity
     * @param clazz
     */
    public static void startActivityRightInRightOut(Activity activity, Class<? extends Activity> clazz) {
        startActivityRightInRightOut(activity, getIntent(activity, clazz));

    }

    private static Intent getIntent(Activity activity, Class<? extends Activity> clazz) {
        return new Intent(activity, clazz);
    }

    public static void startActivityBottomInBottomOut(Activity activity, Class<? extends Activity> clazz) {
        startActivity(activity, getIntent(activity, clazz));

    }

    /**
     * 底部上来，底部出去
     *
     * @param activity
     * @param intent
     */
    public static void startActivityBottomInBottomOut(Activity activity, Intent intent) {
        startActivity(activity, intent, R.anim.push_bottom_in, R.anim.push_bottom_out,
                R.anim.pull_bottom_in, R.anim.pull_bottom_out);

    }

    public static void startActivityCenterInCenterOut(Activity activity, Class<? extends Activity> clazz) {
        startActivityCenterInCenterOut(activity, getIntent(activity, clazz));

    }

    /**
     * 中部向顶部出，顶部向中间退
     *
     * @param activity
     * @param clazz
     */
    public static void startActivityCenterInCenterOut(Activity activity, Intent clazz) {
        startActivity(activity, clazz, R.anim.push_center_in, R.anim.push_center_out,
                R.anim.pull_center_in, R.anim.pull_center_out);

    }


    /**
     * 透明度渐变效果
     *
     * @param activity
     * @param clazz
     */
    public static void startActivityAlpha(Activity activity, Class<? extends Activity> clazz) {
        startActivity(activity, clazz, R.anim.push_alpha_in, R.anim.push_alpha_out,
                R.anim.pull_alpha_in, R.anim.pull_alpha_out);

    }

    /**
     * 渐变效果
     *
     * @param activity
     * @param clazz
     */
    public static void startActivityFade(Activity activity, Class<? extends Activity> clazz) {
        startActivity(activity, clazz, android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);

    }

    /**
     * 使用默认动画关闭，即，打开的时候传进的动画效果
     *
     * @param activity
     */
    public static void finishDefaultAnim(Activity activity) {
        if (!activity.isFinishing()) {
            activity.finish();
        }
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            int enterAnim = bundle.getInt(BUNDLE_KEY_INT_ACTIVITY_FINISH_ENTER_ANIM);
            int exitAnim = bundle.getInt(BUNDLE_KEY__INT_ACTIVITY_FINISH_EXIT_ANIM);
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

}

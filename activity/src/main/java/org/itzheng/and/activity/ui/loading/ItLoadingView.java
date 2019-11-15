package org.itzheng.and.activity.ui.loading;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itzheng.and.activity.R;
import org.itzheng.and.activity.ui.view.AttachView;

/**
 * Title:自己实现的加载进度条<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-4-16.
 */
public class ItLoadingView extends AttachView implements ILoading {

    /**
     * 使用View实例化，最终将显示到最近的 FrameLayout 上
     *
     * @param view
     * @return
     */
    public static ILoading newInstance(Context context, View view) {
        ItLoadingView iLoading = new ItLoadingView(context);
        iLoading.setAttachView(view);
        return iLoading;
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
        super(context);
    }

    @Override
    protected View onCreateView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.it_layout_view_loading, null);
    }

    @Override
    public void showLoading() {
        show();
    }

    @Override
    public void dismissLoading() {
        dismiss();
    }

    @Override
    public boolean isLoading() {
        return isShowing();
    }
}

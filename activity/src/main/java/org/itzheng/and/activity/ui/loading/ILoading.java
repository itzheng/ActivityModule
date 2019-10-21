package org.itzheng.and.activity.ui.loading;

/**
 * Title:加载进度条<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-4-13.
 */
public interface ILoading {
    /**
     * 显示加载提示框
     */
    void showLoading();

    /**
     * 隐藏加载提示框
     */
    void dismissLoading();

//    /**
//     * 设置进度百分比
//     */
//    void setLoadingProcess();

    /**
     * 是否正在加载
     *
     * @return
     */
    boolean isLoading();
}

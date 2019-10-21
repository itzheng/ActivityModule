package org.itzheng.and.activity.ui.toast;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public interface IToast {
    void showToast(int resId);

    void showToast(String string);

    /**
     * 如果使用popup，需要手动 finish，如果使用系统的，则无视这个方法
     */
    void finish();
}

package org.itzheng.and.activity.ui.keyboard;

/**
 * Title:输入法操作类<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2019-10-18.
 */
public interface ISoftInput {
    /**
     * 弹出键盘
     */
    void showSoftInput();

    /**
     * 关闭键盘
     */
    void hideSoftInput();


    /**
     * 输入法是否弹出
     *
     * @return
     */
    boolean isSoftInputShowing();

    /**
     * 键盘弹出关闭监听，有些界面的 View 需要根据键盘状态进行显示隐藏
     *
     * @param callback
     */
    void setOnSoftInputChangedCallback(OnSoftInputChanged callback);

    /**
     * 键盘监听
     */
    interface OnSoftInputChanged {
        /**
         * 键盘弹出
         */
        void onSoftInputShow();

        /**
         * 键盘隐藏
         */
        void onSoftInputHide();
    }
}

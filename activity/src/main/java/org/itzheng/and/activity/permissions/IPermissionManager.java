package org.itzheng.and.activity.permissions;

import android.Manifest;
import android.support.annotation.NonNull;

/**
 * Title:权限请求工具<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-7-11.
 */
public interface IPermissionManager {
    /**
     * 请求某些权限，如果本身都具备权限了就直接通过，只有当没有权限才会使用请求功能。
     *
     * @param listener    结果回调
     * @param permissions {@link Manifest.permission}
     */
    void requestPermissions(OnRequestPermissionListener listener, String... permissions);

    /**
     * 请求权限的回调，FragmentActivity 自带
     * 该方法应该会至少执行2次，第一次，请求的时候，@param permissions 返回空，第二次，@param permissions返回请求的权限
     *
     * @param requestCode  请求码，因为害怕每次执行不同，造成遗漏的回调，每次请求码递增
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void onDestroy();

    interface OnRequestPermissionListener {
        /**
         * 用户同意的权限
         *
         * @param isAllGrant  是否全部同意，如果只请求一个这个当然没有意义，如果请求多个，当需要全部权限都通过时，这个就用的到了
         * @param permissions 同意的权限
         */
        void grantPermissions(boolean isAllGrant, String[] permissions);

        /**
         * 用户拒绝的权限
         *
         * @param isAllDeny   是否全部拒绝
         * @param permissions 拒绝的权限
         */
        void denyPermissions(boolean isAllDeny, String[] permissions);
    }
}

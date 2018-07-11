package org.itzheng.and.activity.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-7-11.
 */
public class PermissionHelper implements IPermissionManager {
    private Activity mContext;
    /**
     * 权限请求码：默认值0，每请求一个则递增1
     */
    private int permissionRequestCode = 0;
    private Map<Integer, OnRequestPermissionListener> permissionListenerMap = new HashMap<>();

    public PermissionHelper(Activity context) {
        mContext = context;
    }

    @Override
    public void requestPermissions(OnRequestPermissionListener listener, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0后才需要权限管理
            if (lacksPermission(mContext, permissions)) {
                permissionRequestCode++;
                if (listener != null) {
                    //如果请求监听不为空，则将监听缓存起来
                    permissionListenerMap.put(permissionRequestCode, listener);
                }
                //进行权限请求
                ActivityCompat.requestPermissions(mContext, permissions, permissionRequestCode);
            } else {
                //如果拥有权限则不需要处理
                if (listener != null) {
                    listener.grantPermissions(true, permissions);
                }
            }
        } else {
            if (listener != null) {
                listener.grantPermissions(true, permissions);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //每次发起请求，onRequestPermissionsResult都会执行，第一次，
        if (permissions == null || permissions.length == 0) {
            //如果请求为空，则不执行回调
            return;
        }
        OnRequestPermissionListener listeners = permissionListenerMap.get(requestCode);
        if (listeners != null) {
            ArrayList<String> grantList = new ArrayList<>();
            ArrayList<String> denyList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults.length <= i) {
                    break;
                }
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {//允许
                    grantList.add(permissions[i]);
                } else if (grantResult == PackageManager.PERMISSION_DENIED) {//拒绝
                    denyList.add(permissions[i]);
                }
            }
            String[] grantPermissions = grantList.toArray(new String[grantList.size()]);
            String[] denyPermissions = denyList.toArray(new String[denyList.size()]);
//            listeners.requestPermissions(permissions);
            if (grantPermissions != null && grantPermissions.length > 0) {
                //只要有一个同意就执行同意的回调，并且将同意列表传递过去
                listeners.grantPermissions(grantPermissions.length == permissions.length, grantPermissions);
            }
            if (denyPermissions != null && denyPermissions.length > 0) {
                //只要有一个拒绝，就执行拒绝回调，并且将拒绝列表传递过去
                listeners.denyPermissions(denyPermissions.length == permissions.length, denyPermissions);
            }
            //最后移除监听
            permissionListenerMap.remove(requestCode);
        }
    }

    @Override
    public void onDestroy() {
        //界面销毁，及时清除缓存，避免内存泄漏
        if (permissionListenerMap != null)
            permissionListenerMap.clear();
    }

    /**
     * 判断是否缺少权限
     *
     * @param permissions
     * @return
     */

    public static boolean lacksPermission(Context contexts, String[] permissions) {
        if (permissions == null) {
            //如果传入权限为空，说明没有缺少权限
            return false;
        }
        for (String permission : permissions) {
            //只要一个权限缺少就认为整组都缺少
            if (ContextCompat.checkSelfPermission(contexts, permission) ==
                    PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }
}

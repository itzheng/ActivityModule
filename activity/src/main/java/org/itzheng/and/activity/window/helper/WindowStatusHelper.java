package org.itzheng.and.activity.window.helper;

import android.app.Activity;
import android.os.Build;

import org.itzheng.and.activity.window.IWindowStatus;
import org.itzheng.and.activity.window.proxy.WindowStatus_v00;
import org.itzheng.and.activity.window.proxy.WindowStatus_v19;
import org.itzheng.and.activity.window.proxy.WindowStatus_v21;
import org.itzheng.and.activity.window.proxy.WindowStatus_v23;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-2-24.
 */
public class WindowStatusHelper {
    public static IWindowStatus newInstance(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return WindowStatus_v23.newInstance(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return WindowStatus_v21.newInstance(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return WindowStatus_v19.newInstance(activity);
        }
        return WindowStatus_v00.newInstance(activity);
    }

    private WindowStatusHelper() {

    }
}

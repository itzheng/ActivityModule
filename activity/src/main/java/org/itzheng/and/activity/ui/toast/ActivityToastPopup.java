package org.itzheng.and.activity.ui.toast;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title:模仿系统Toast，只能在当前Activity显示<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-11-12.
 */
public class ActivityToastPopup extends PopupWindow {
    TextView tv;

    public ActivityToastPopup(final Context context) {
        super(context);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(false);
        setOutsideTouchable(false);
        View view = Toast.makeText(context, text, Toast.LENGTH_SHORT).getView();
        if (view != null) {
            tv = (TextView) view.findViewById(android.R.id.message);
            tv.setText(text);
            setContentView(view);
        }
        setAnimationStyle(android.R.style.Animation_Toast);
    }

    private String text = "";

    public void makeText(String text) {
        this.text = text;
        if (tv != null) {
            tv.setText(text);
        }
        createHandler();
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };
    private static final int LONG_DELAY = 3500; // 3.5 seconds

    private static final int SHORT_DELAY = 2000; // 2 seconds

    private void createHandler() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, SHORT_DELAY);
    }

    @Override
    public void dismiss() {
        handler.removeCallbacks(runnable);
        super.dismiss();
    }

    /**
     * 是否使用系统通知toast
     */
    public static boolean isSystemToast(Context context) {
        //之后才有这个特性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            boolean isOpened = manager.areNotificationsEnabled();
            return isOpened;
        }
        return true;
    }

    /**
     * 打开允许通知的设置页
     */
    public static void goToNotificationSetting(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

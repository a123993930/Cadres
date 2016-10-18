package com.itianyi.app;

/**
 * Created by 沫 on 2015/7/30.
 */

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 监听是否点击了home键将客户端推到后台
 */
public class mHomeKeyEventReceiver extends BroadcastReceiver {
    String SYSTEM_REASON = "reason";
    String SYSTEM_HOME_KEY = "homekey";
    String SYSTEM_HOME_KEY_LONG = "recentapps";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                //表示按了home键,程序到了后台
                //关闭所有的界面
            } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                //表示长按home键,显示最近使用的程序列表
            }
        }
    }
}

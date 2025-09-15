package com.crimson.app.heartsteel.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import com.crimson.app.heartsteel.ContextHolder;

/**
 * 深色模式工具类：判断状态 + 监听变化
 */
public class AppearanceUtil {

    public static boolean isDarkMode(Context context) {
        if (context == null) {
            return false;
        }
        // 获取系统配置
        var configuration = context.getResources().getConfiguration();
        // 提取夜间模式状态（API 8+ 兼容）
        int nightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        // 判断是否为深色模式
        return nightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static boolean isWatch(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH);
    }

}

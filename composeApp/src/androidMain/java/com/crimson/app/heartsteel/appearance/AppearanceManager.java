package com.crimson.app.heartsteel.appearance;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsetsController;

import com.crimson.app.heartsteel.ContextHolder;
import com.crimson.app.heartsteel.reactivex.Topic;
import com.crimson.app.heartsteel.reactivex.client.InnerMqClient;
import com.crimson.app.heartsteel.reactivex.service.InnerMqService;
import com.crimson.app.heartsteel.util.AppearanceUtil;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AppearanceManager {

    protected final Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient darkModeChangeReceiveClient;

    public AppearanceManager() {
    }

    public void statusBarToDark(boolean flag) {
        var context = ContextHolder.INSTANCE.getContext();
        if (context == null) {
            return;
        }
        this.mainHandler.post(() -> {
            var window = context.getWindow();
            if (this.supportDarkMode()) {
                // Android 10+
                var controller = window.getInsetsController();
                if (controller != null) {
                    controller.setSystemBarsAppearance(
                            flag ? 0 : WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    );
                }
            } else {
                // Android 6.0+（API 23-29）：兼容处理
                var decorView = window.getDecorView();
                var systemUiVisibility = decorView.getSystemUiVisibility();
                if (flag) {
                    // 移除深色图标标志（恢复白色）
                    systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    // 添加深色图标标志
                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(systemUiVisibility);
            }
        });
    }

    public boolean supportDarkMode() {
        // Android 10+ 且 不为手表
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !isWatch();
    }

    public boolean isDarkMode() {
        if (this.supportDarkMode()) {
            var context = ContextHolder.INSTANCE.getContext();
            if (context == null) {
                return false;
            }
            return AppearanceUtil.isDarkMode(context);
        } else {
            return false;
        }
    }

    public void addDarkModeChangeCallback(Function1<Boolean, Unit> callback) {
        try {
            this.darkModeChangeReceiveClient = this.innerMqService.createClient();
            this.darkModeChangeReceiveClient.<Boolean>sub(Topic.DARK_MODE_CHANGE, (res) -> {
                this.mainHandler.post(() -> {
                    callback.invoke(res);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDarkModeChangeCallback() {
        this.innerMqService.destroyClient(this.darkModeChangeReceiveClient);
    }

    public boolean isWatch() {
        return AppearanceUtil.isWatch(ContextHolder.INSTANCE.getContext());
    }

    public boolean isForeground() {
        return AppLifecycleObserver.isForeground();
    }

}

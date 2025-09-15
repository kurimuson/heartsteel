package com.crimson.app.heartsteel

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ProcessLifecycleOwner
import com.crimson.app.heartsteel.appearance.AppLifecycleObserver
import com.crimson.app.heartsteel.reactivex.Topic
import com.crimson.app.heartsteel.reactivex.service.InnerMqService


class MainActivity : ComponentActivity() {

    private val innerMqService: InnerMqService? = InnerMqService.getInstance()

    private fun isWatch(): Boolean {
        return this.packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
    }

    @SuppressLint("SourceLockedOrientationActivity", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        // 判断是否为watch
        if (isWatch()) {
            // 1. 定义固定DPI值（根据圆形手表的视觉需求调整，例如200dpi）
            // 建议值：160-240之间，根据实际测试效果微调
            val fixedDpi = 140 // DisplayMetrics.DENSITY_LOW
            val metrics = resources.displayMetrics
            val config = resources.configuration
            // 2. 更新密度相关参数（基于固定DPI）
            metrics.densityDpi = fixedDpi
            // density = dpi / 160（Android默认换算公式）
            metrics.density = fixedDpi / 160f
            // scaledDensity通常与density一致（字体缩放）
            metrics.scaledDensity = metrics.density
            // 3. 更新屏幕尺寸的dp值（基于固定DPI重新计算）
            // 公式：dp = 像素值 / density
            config.screenWidthDp = (metrics.widthPixels / metrics.density).toInt()
            config.screenHeightDp = (metrics.heightPixels / metrics.density).toInt()
            config.densityDpi = fixedDpi
            // 4. 应用配置（注意：updateConfiguration已废弃，推荐使用createConfigurationContext）
            resources.updateConfiguration(config, metrics)
        }
        // 创建界面
        super.onCreate(savedInstanceState)
        // 保持竖屏
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        // 注册观察者到应用生命周期所有者
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
        // 保存Context实例
        ContextHolder.init(this)
        // 显示界面
        setContent {
            App()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                innerMqService?.pub(Topic.DARK_MODE_CHANGE, false)
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                innerMqService?.pub(Topic.DARK_MODE_CHANGE, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

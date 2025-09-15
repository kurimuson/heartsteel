package com.crimson.app.heartsteel.player.base;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.crimson.app.heartsteel.common.ResourceDataParam;
import com.crimson.app.heartsteel.model.AudioInfo;
import com.crimson.app.heartsteel.model.PlayTime;
import com.crimson.app.heartsteel.player.core.OboeEngine;
import com.crimson.app.heartsteel.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public abstract class OboePlayer {

    protected final Context context;
    protected final Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程
    protected final HandlerThread audioThread; // 事务处理专用子线程
    protected Handler handler; // 绑定到子线程的Handler，用于发送任务
    protected Thread timeUpdateThread; // 时间更新子线程
    protected volatile boolean isTimeUpdating = false; // 线程运行标记（volatile确保多线程可见性）
    protected final long interval = 50; // 50ms更新一次

    protected final Map<String, AudioInfo> audioInfoMap = new HashMap<>();
    protected final Set<String> trackList = new HashSet<>();
    protected boolean isInitialled = false;
    protected int step = 1;
    protected boolean isLocked = false;
    protected boolean isInProcessing = false;
    protected boolean isPlaying = false;
    protected boolean isTurning = false;

    protected Map<String, Function1<Boolean, Unit>> playStateCallbackMap = new HashMap<>();
    protected Map<String, Function1<PlayTime, Unit>> playTimeCallbackMap = new HashMap<>();
    protected Map<String, Function1<Boolean, Unit>> controlEnableCallbackMap = new HashMap<>();
    protected Map<String, Function0<Unit>> onAutoStopCallbackMap = new HashMap<>();

    // 音频输出设备更换（蓝牙耳机断开连接）
    private final AtomicInteger oboeErrorCount = new AtomicInteger(0);
    protected OboeEngine.OboeErrorCallback oboeErrorCallback = (err) -> {
        if (this.oboeErrorCount.get() < this.audioInfoMap.size() - 1) {
            this.oboeErrorCount.addAndGet(1);
            this.isPlaying = false;
            this.isInProcessing = true;
            this.doControlEnableCallback(false);
            this.doPlayTimeCallback(new PlayTime(this.getPlayCurrentTimeMillis(), ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)));
            this.doPlayStateCallback(false);
        } else {
            this.oboeErrorCount.set(0);
            this.handler.post(() -> {
                this.stopTimeUpdateThread();
                var currentTime = this.getPlayCurrentTimeMillis();
                for (var entry : this.audioInfoMap.entrySet()) {
                    var engine = entry.getValue().getEngine();
                    engine.destroy();
                }
                this.audioInfoMap.clear();
                System.gc();
                this.initMediaPlayer(() -> {
                    for (var entry : this.audioInfoMap.entrySet()) {
                        var engine = entry.getValue().getEngine();
                        engine.turnTo(currentTime);
                    }
                    this.isInProcessing = false;
                    this.doControlEnableCallback(true);
                    return null;
                });
            });
        }
    };

    public OboePlayer(Context context) {
        this.context = context;
        // 初始化音频子线程（带Looper的HandlerThread）
        // 创建绑定到子线程Looper的Handler，所有任务通过此Handler发送到子线程
        this.audioThread = new HandlerThread("AudioPlayerThread");
        this.audioThread.start(); // 启动子线程，内部会创建Looper
        this.handler = new Handler(this.audioThread.getLooper());
    }

    public boolean getIsInitialled() {
        return this.isInitialled;
    }

    public boolean getIsPlaying() {
        return this.isPlaying;
    }

    public PlayTime getCurrentPlayTime() {
        var time = this.getPlayCurrentTimeMillis();
        var all = ResourceDataParam.GET_MUSIC_STEP_TIME(this.step);
        if (this.trackList.isEmpty()) {
            all = 0;
        }
        return new PlayTime(time, all);
    }

    public boolean getIsControlEnable() {
        return !this.isInProcessing && !this.isLocked;
    }

    public float[] getCurrentVoiceHigh() {
        var list = new ArrayList<short[]>();
        // 收集所有非空的short数组（过滤无效数据）
        for (var each : this.audioInfoMap.values()) {
            short[] voiceHigh = each.getEngine().getCurrentVoiceHigh();
            if (voiceHigh != null && voiceHigh.length > 0) {
                list.add(voiceHigh);
            }
        }
        // 处理空列表情况，避免空指针
        if (list.isEmpty()) {
            return new float[0];
        }
        // 确定数组长度（以第一个有效数组为准）
        int length = list.get(0).length;
        float[] result = new float[length];
        // 遍历每个索引位置，计算所有音轨的平均值
        for (int i = 0; i < length; i++) {
            double sum = 0; // 用long避免累加溢出（支持多音轨累加）
            int count = 0;
            // 累加每个音轨在当前索引的无符号值
            for (short[] voiceHigh : list) {
                // 确保索引不越界（处理数组长度不一致的极端情况）
                if (i < voiceHigh.length) {
                    // 将short转为无符号int（还原C++中uint16_t的0~65535范围）
                    int unsignedValue = voiceHigh[i] & 0xFFFF;
                    sum += unsignedValue;
                    count++;
                }
            }
            // 计算平均值（处理无有效数据的情况）
            if (count > 0) {
                result[i] = (float) (sum / count); // 转为float平均值
            } else {
                result[i] = 0.0f;
            }
        }
        return result;
    }

    public void lockControl() {
        this.isLocked = true;
        this.doControlEnableCallback(false);
    }

    public void unlockControl() {
        this.isLocked = false;
        this.doControlEnableCallback(!this.isInProcessing);
    }

    protected void initMediaPlayer(Function0<Unit> onFinish) {
        this.isInitialled = true;
        this.handler.post(() -> {
            for (var track : this.trackList) {
                var filePath = Objects.requireNonNull(ResourceDataParam.GET_MUSIC_STEP_TRACK_MAP(this.step).get(track));
                var engine = new OboeEngine(this.context.getAssets(), filePath, this.oboeErrorCallback);
                var audioInfo = new AudioInfo(track, filePath, engine);
                this.audioInfoMap.put(track, audioInfo);
            }
            if (onFinish != null) {
                onFinish.invoke();
            }
        });
    }

    public void play(Function0<Unit> onFinish) {
        if (this.isLocked || this.isInProcessing) {
            this.finishInvoke(onFinish);
            return;
        }
        if (this.trackList.isEmpty() || this.isPlaying) {
            this.finishInvoke(onFinish);
            return;
        }
        this.isPlaying = true;
        this.isInProcessing = true;
        this.doControlEnableCallback(false);
        this.doPlayStateCallback(true);
        this.doPlayTimeCallback(new PlayTime(this.getPlayCurrentTimeMillis(), ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)));
        this.doPlayStateCallback(true);
        if (this.audioInfoMap.isEmpty()) {
            this.initMediaPlayer(null);
        }
        this.handler.post(() -> {
            // 音量
            for (var entry : this.audioInfoMap.entrySet()) {
                var track = entry.getKey();
                var engine = entry.getValue().getEngine();
                var volume = Objects.requireNonNull(ResourceDataParam.GET_MUSIC_STEP_VOLUME_MAP(this.step).get(track));
                engine.setVolume(volume);
            }
            // 播放
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                engine.play();
            }
            // 同步
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                engine.turnTo(this.getPlayCurrentTimeMillis());
            }
            // 监听与回调
            this.startTimeUpdateThread();
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
            this.finishInvoke(onFinish);
        });
    }

    public void pause(Function0<Unit> onFinish) {
        if (this.isLocked || this.isInProcessing) {
            this.finishInvoke(onFinish);
            return;
        }
        if (!this.isPlaying) {
            this.finishInvoke(onFinish);
            return;
        }
        this.isPlaying = false;
        this.isInProcessing = true;
        this.doControlEnableCallback(false);
        this.doPlayTimeCallback(new PlayTime(this.getPlayCurrentTimeMillis(), ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)));
        this.doPlayStateCallback(false);
        this.handler.post(() -> {
            this.stopTimeUpdateThread();
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                engine.pause();
            }
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
            this.finishInvoke(onFinish);
        });
    }

    public void stop(Function0<Unit> onFinish) {
        if (this.isLocked || this.isInProcessing) {
            this.finishInvoke(onFinish);
            return;
        }
        this.isPlaying = false;
        this.isInProcessing = true;
        this.doControlEnableCallback(false);
        this.doPlayTimeCallback(new PlayTime(this.getPlayCurrentTimeMillis(), ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)));
        this.doPlayStateCallback(false);
        this.handler.post(() -> {
            this.stopTimeUpdateThread();
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                engine.stop();
            }
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
            this.finishInvoke(onFinish);
        });
    }

    public void turnTo(int millis, Function0<Unit> onFinish) {
        if (this.isLocked || this.isInProcessing) {
            this.finishInvoke(onFinish);
            return;
        }
        this.isTurning = true;
        this.isInProcessing = true;
        this.doControlEnableCallback(false);
        this.handler.post(() -> {
            this.stopTimeUpdateThread();
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                if (this.isPlaying) {
                    engine.pause();
                }
                engine.turnTo(millis);
                if (this.isPlaying) {
                    engine.play();
                }
            }
            this.isTurning = false;
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
            this.startTimeUpdateThread();
            this.finishInvoke(onFinish);
        });
    }

    protected void startTimeUpdateThread() {
        if (this.isTimeUpdating || this.timeUpdateThread != null && this.timeUpdateThread.isAlive()) {
            return;
        }
        this.isTimeUpdating = true;
        this.timeUpdateThread = new Thread(() -> {
            while (this.isTimeUpdating) {
                try {
                    // 更新时间
                    var time = this.getPlayCurrentTimeMillis();
                    if (!this.isTurning) {
                        this.doPlayTimeCallback(new PlayTime(time, ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)));
                    }
                    if (time >= ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)) {
                        this.stopTimeUpdateThread();
                        this.stop(() -> {
                            this.doOnAutoStopCallback();
                            return null;
                        });
                    }
//                    var list = getCurrentVoiceHigh();
//                    var sss = new StringBuffer();
//                    for (var b : list) {
//                        sss.append(b);
//                        sss.append(", ");
//                    }
//                    System.out.println(sss);
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                    // 线程被中断时退出循环（如pause/stop时）
                    Thread.currentThread().interrupt(); // 保留中断标记
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            // 线程结束时重置状态
            this.isTimeUpdating = false;
            this.timeUpdateThread = null;
        }, "AudioTimeUpdater"); // 命名线程便于调试
        this.timeUpdateThread.start();
    }

    protected void stopTimeUpdateThread() {
        if (!this.isTimeUpdating && this.timeUpdateThread == null || !this.timeUpdateThread.isAlive()) {
            return;
        }
        // 标记线程停止
        this.isTimeUpdating = false;
        // 使用同步块确保线程引用的可见性
        synchronized (this) {
            if (this.timeUpdateThread != null) {
                // 中断线程（如果它在休眠中会被唤醒）
                this.timeUpdateThread.interrupt();
                try {
                    // 阻塞等待线程完全终止，没有超时时间
                    this.timeUpdateThread.join();
                } catch (InterruptedException e) {
                    // 保留中断状态
                    Thread.currentThread().interrupt();
                }
                // 线程已终止，清除引用
                this.timeUpdateThread = null;
            }
        }
    }

    protected int getPlayCurrentTimeMillis() {
        if (this.audioInfoMap.isEmpty()) {
            return 0;
        }
        var first = this.audioInfoMap.entrySet().stream().findFirst();
        if (first.isEmpty()) {
            return 0;
        }
        var info = first.get().getValue();
        var engine = info.getEngine();
        var time = engine.getCurrentPosition();
        if (time >= ResourceDataParam.GET_MUSIC_STEP_TIME(this.step)) {
            time = ResourceDataParam.GET_MUSIC_STEP_TIME(this.step);
        }
        return (int) time;
    }

    public String addPlayStateCallback(Function1<Boolean, Unit> callback) {
        var uid = CommonUtils.generateCharMixedExt(16) + "___" + System.currentTimeMillis();
        this.playStateCallbackMap.put(uid, callback);
        return uid;
    }

    public void removePlayStateCallback(String uid) {
        this.playStateCallbackMap.remove(uid);
    }

    public String addPlayTimeCallback(Function1<PlayTime, Unit> callback) {
        var uid = CommonUtils.generateCharMixedExt(16) + "___" + System.currentTimeMillis();
        this.playTimeCallbackMap.put(uid, callback);
        return uid;
    }

    public void removePlayTimeCallback(String uid) {
        this.playTimeCallbackMap.remove(uid);
    }

    public String addControlEnableCallback(Function1<Boolean, Unit> callback) {
        var uid = CommonUtils.generateCharMixedExt(16) + "___" + System.currentTimeMillis();
        this.controlEnableCallbackMap.put(uid, callback);
        return uid;
    }

    public void removeControlEnableCallback(String uid) {
        this.controlEnableCallbackMap.remove(uid);
    }

    public String addOnAutoStopCallback(Function0<Unit> callback) {
        var uid = CommonUtils.generateCharMixedExt(16) + "___" + System.currentTimeMillis();
        this.onAutoStopCallbackMap.put(uid, callback);
        return uid;
    }

    public void removeOnAutoStopCallback(String uid) {
        this.onAutoStopCallbackMap.remove(uid);
    }

    protected void doPlayStateCallback(boolean value) {
        this.mainHandler.post(() -> {
            for (var callback : this.playStateCallbackMap.values()) {
                callback.invoke(value);
            }
        });
    }

    protected void doPlayTimeCallback(PlayTime value) {
        this.mainHandler.post(() -> {
            for (var callback : this.playTimeCallbackMap.values()) {
                callback.invoke(value);
            }
        });
    }

    protected void doControlEnableCallback(boolean value) {
        this.mainHandler.post(() -> {
            for (var callback : this.controlEnableCallbackMap.values()) {
                callback.invoke(value && !this.isLocked);
            }
        });
    }

    protected void doOnAutoStopCallback() {
        this.mainHandler.post(() -> {
            for (var callback : this.onAutoStopCallbackMap.values()) {
                callback.invoke();
            }
        });
    }

    protected void finishInvoke(Function0<Unit> onFinish) {
        if (onFinish != null) {
            this.mainHandler.post(onFinish::invoke);
        }
    }

    public void release(Function0<Unit> onFinish) {
        if (!this.isInitialled) {
            this.finishInvoke(onFinish);
            return;
        }
        this.isInitialled = false;
        this.isPlaying = false;
        this.isInProcessing = true;
        this.doControlEnableCallback(false);
        this.doPlayStateCallback(false);
        this.doPlayTimeCallback(new PlayTime(0, 0));
        this.handler.post(() -> {
            this.stopTimeUpdateThread();
            for (var value : this.audioInfoMap.values()) {
                var player = value.getEngine();
                player.stop();
            }
            for (var value : this.audioInfoMap.values()) {
                var player = value.getEngine();
                player.destroy();
            }
            this.audioInfoMap.clear();
            System.gc();
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
            this.finishInvoke(onFinish);
        });
    }

    public void destroy(Function0<Unit> onFinish) {
        // 停止播放并释放MediaPlayer
        this.release(onFinish);
        // 停止原HandlerThread子线程
        this.audioThread.quitSafely();
        try {
            this.audioThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

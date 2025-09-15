package com.crimson.app.heartsteel.player;

import android.content.Context;

import com.crimson.app.heartsteel.model.TraitSave;
import com.crimson.app.heartsteel.player.base.OboePlayer;
import com.crimson.app.heartsteel.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class OboeListPlayer extends OboePlayer {

    protected Map<String, Function1<String, Unit>> playUidUpdateCallbackMap = new HashMap<>();

    private final List<TraitSave> playList = new ArrayList<>();
    private String onAutoStopCallbackUid;
    private String currentPlayUid = "";

    public OboeListPlayer(Context context) {
        super(context);
    }

    public String getCurrentPlayUid() {
        return this.currentPlayUid;
    }

    public void updatePlayList(List<TraitSave> list) {
        this.playList.clear();
        this.playList.addAll(list);
    }

    public void listPlay(String startAtUid, Function0<Unit> onFinish) {
        if (this.playList.isEmpty()) {
            this.finishInvoke(onFinish);
            return;
        }
        if (this.isPlaying || startAtUid == null || startAtUid.isEmpty() || !this.currentPlayUid.equals(startAtUid)) {
            // 切换歌曲，先停止并释放再播放
            super.release(() -> {
                this.listPlayStart(startAtUid, onFinish);
                return null;
            });
        } else {
            // 继续播放
            this.play(onFinish);
        }
    }

    private void listPlayStart(String startAtUid, Function0<Unit> onFinish) {
        this.removeOnAutoStopCallback(this.onAutoStopCallbackUid);
        if (startAtUid == null || startAtUid.isEmpty()) {
            this.currentPlayUid = String.valueOf(this.playList.get(0).getUid());
        } else {
            this.currentPlayUid = startAtUid;
        }
        this.doPlayUidUpdateCallback(this.currentPlayUid);
        this.updateTrackListByUid(this.currentPlayUid);
        this.play(onFinish);
        this.onAutoStopCallbackUid = this.addOnAutoStopCallback(() -> {
            this.playNext(null);
            return null;
        });
    }

    public void playPre(Function0<Unit> onFinish) {
        if (this.currentPlayUid.isEmpty() || this.playList.isEmpty()) {
            this.finishInvoke(onFinish);
            return;
        }
        var currentIndex = this.getIndexByUid();
        if (currentIndex == 0) {
            currentIndex = this.playList.size() - 1;
        } else {
            currentIndex -= 1;
        }
        this.currentPlayUid = String.valueOf(this.playList.get(currentIndex).getUid());
        this.doPlayUidUpdateCallback(this.currentPlayUid);
        this.updateTrackListByUid(this.currentPlayUid);
        super.release(() -> {
            this.play(onFinish);
            return null;
        });
    }

    public void playNext(Function0<Unit> onFinish) {
        if (this.currentPlayUid.isEmpty() || this.playList.isEmpty()) {
            this.finishInvoke(onFinish);
            return;
        }
        var currentIndex = this.getIndexByUid();
        if (currentIndex == this.playList.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex += 1;
        }
        this.currentPlayUid = String.valueOf(this.playList.get(currentIndex).getUid());
        this.doPlayUidUpdateCallback(this.currentPlayUid);
        this.updateTrackListByUid(this.currentPlayUid);
        super.release(() -> {
            this.play(onFinish);
            return null;
        });
    }

    private void updateTrackListByUid(String uid) {
        for (var each : this.playList) {
            if (uid.equals(String.valueOf(each.getUid()))) {
                this.step = Integer.parseInt(String.valueOf(each.getStep()));
                this.trackList.clear();
                this.trackList.addAll(new ArrayList<>(each.getTrackList()));
                break;
            }
        }
    }

    private int getIndexByUid() {
        var index = 0;
        for (var i = 0; i < this.playList.size(); i++) {
            if (String.valueOf(this.playList.get(i).getUid()).equals(this.currentPlayUid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public String addPlayUidUpdateCallback(Function1<String, Unit> callback) {
        var uid = CommonUtils.generateCharMixedExt(16) + "___" + System.currentTimeMillis();
        this.playUidUpdateCallbackMap.put(uid, callback);
        return uid;
    }

    public void removePlayUidUpdateCallback(String uid) {
        this.playUidUpdateCallbackMap.remove(uid);
    }

    private void doPlayUidUpdateCallback(String value) {
        this.mainHandler.post(() -> {
            for (var callback : this.playUidUpdateCallbackMap.values()) {
                callback.invoke(value);
            }
        });
    }

    private void reset() {
        this.removeOnAutoStopCallback(this.onAutoStopCallbackUid);
        this.currentPlayUid = "";
        this.playList.clear();
    }

    @Override
    public void release(Function0<Unit> onFinish) {
        this.reset();
        super.release(onFinish);
    }

    @Override
    public void destroy(Function0<Unit> onFinish) {
        this.removeOnAutoStopCallback(this.onAutoStopCallbackUid);
        super.destroy(onFinish);
    }

}

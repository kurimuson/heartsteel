package com.crimson.app.heartsteel.player;

import android.content.Context;

import com.crimson.app.heartsteel.common.ResourceDataParam;
import com.crimson.app.heartsteel.model.AudioInfo;
import com.crimson.app.heartsteel.model.PlayTime;
import com.crimson.app.heartsteel.player.base.OboePlayer;
import com.crimson.app.heartsteel.player.core.OboeEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class OboeMixerPlayer extends OboePlayer {

    public OboeMixerPlayer(Context context) {
        super(context);
    }

    public void updateStep(int step, Function0<Unit> onFinish) {
        if (this.step != step) {
            this.release(onFinish);
        }
        this.step = step;
    }

    public void updateTrackList(List<String> trackList, Function0<Unit> onFinish) {
        var set1 = new HashSet<>(this.trackList);
        var set2 = new HashSet<>(trackList);
        if (set1.equals(set2)) {
            this.finishInvoke(onFinish);
            return;
        }
        this.trackList.clear();
        this.trackList.addAll(trackList);
        if (this.isInProcessing) {
            this.finishInvoke(onFinish);
            return;
        }
        if (this.isInitialled) {
            if (trackList.isEmpty()) {
                this.stop(() -> {
                    this.finishInvoke(onFinish);
                    return null;
                });
                this.doPlayTimeCallback(new PlayTime(0, 0));
            } else {
                this.updateTrackInPlaying(set1, set2);
            }
        }
    }

    private void updateTrackInPlaying(HashSet<String> oldSet, HashSet<String> newSet) {
        if (this.isInProcessing) {
            return;
        }
        this.handler.post(() -> {
            this.isInProcessing = true;
            this.doControlEnableCallback(false);
            // 找出需要移出的音轨
            var shouldRemoveSet = new HashSet<String>();
            for (var v : oldSet) {
                if (!newSet.contains(v)) {
                    shouldRemoveSet.add(v);
                }
            }
            // 找出需要添加的音轨
            var shouldAddSet = new HashSet<String>();
            for (var v : newSet) {
                if (!oldSet.contains(v)) {
                    shouldAddSet.add(v);
                }
            }
            // audioInfoMap删除需要移出的音轨
            for (var v : shouldRemoveSet) {
                var audioInfo = Objects.requireNonNull(this.audioInfoMap.get(v));
                audioInfo.getEngine().stop();
                audioInfo.getEngine().destroy();
                this.audioInfoMap.remove(audioInfo.getName());
            }
            // audioInfoMap实例化需要添加的音轨
            var map = new HashMap<String, AudioInfo>();
            for (var v : shouldAddSet) {
                var filePath = Objects.requireNonNull(ResourceDataParam.GET_MUSIC_STEP_TRACK_MAP(this.step).get(v));
                var engine = new OboeEngine(this.context.getAssets(), filePath, this.oboeErrorCallback);
                var audioInfo = new AudioInfo(v, filePath, engine);
                // 同步新添加的音轨
                engine.turnTo(this.getPlayCurrentTimeMillis());
                if (this.isPlaying) {
                    var volume = Objects.requireNonNull(ResourceDataParam.GET_MUSIC_STEP_VOLUME_MAP(this.step).get(v));
                    engine.setVolume(volume);
                    engine.play();
                }
                map.put(v, audioInfo);
            }
            // 同步所有音轨
            for (var entry : this.audioInfoMap.entrySet()) {
                var engine = entry.getValue().getEngine();
                engine.turnTo(this.getPlayCurrentTimeMillis());
            }
            // 存入map
            this.audioInfoMap.putAll(map);
            this.isInProcessing = false;
            this.doControlEnableCallback(true);
        });
    }

}

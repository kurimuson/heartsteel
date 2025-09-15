package com.crimson.app.heartsteel.appearance;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class AppLifecycleObserver implements DefaultLifecycleObserver {

    private static boolean isForeground = false;

    @Override
    public void onStart(LifecycleOwner owner) {
        isForeground = true;
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        isForeground = false;
    }

    public static boolean isForeground() {
        return isForeground;
    }

}

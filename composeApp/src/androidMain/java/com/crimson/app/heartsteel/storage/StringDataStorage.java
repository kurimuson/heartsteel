package com.crimson.app.heartsteel.storage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class StringDataStorage {

    private static final String DATA_STORE_NAME = "app_storage";

    private final Context context;
    private final Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程
    private RxDataStore<Preferences> dataStore = null;

    public StringDataStorage(Context context) {
        this.context = context;
    }

    private void checkDataStore() {
        if (this.dataStore == null) {
            this.dataStore = new RxPreferenceDataStoreBuilder(this.context, DATA_STORE_NAME).build();
        }
    }


    public void saveData(String key, String data) {
        this.checkDataStore();
        var preferencesKeys = PreferencesKeys.stringKey(key);
        this.dataStore.updateDataAsync(prefsIn -> {
            var mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(preferencesKeys, data);
            return Single.just(mutablePreferences);
        });
    }

    public void loadData(String key, Function1<String, Unit> onResult) {
        this.checkDataStore();
        var preferencesKey = PreferencesKeys.stringKey(key);
        var sub = this.dataStore.data()
                .map((prefs) -> {
                    var res = prefs.get(preferencesKey);
                    return res != null ? res : "";
                })
                .subscribeOn(Schedulers.io())
                .take(1)
                .subscribe((value) -> {
                    // System.out.println(value);
                    this.mainHandler.post(() -> {
                        onResult.invoke(value);
                    });
                });
    }

}

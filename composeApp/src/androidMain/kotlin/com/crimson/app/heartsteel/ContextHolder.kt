package com.crimson.app.heartsteel

import android.app.Activity
import java.lang.ref.WeakReference

object ContextHolder {

    private var appContext: MainActivity? = null
    private var activityRef: WeakReference<MainActivity>? = null

    fun init(app: MainActivity) {
        appContext = app
    }

    fun setCurrentActivity(activity: MainActivity) {
        activityRef = WeakReference(activity)
    }

    fun getActivity(): Activity? = activityRef?.get()
    fun getContext(): Activity? = getActivity() ?: appContext

}
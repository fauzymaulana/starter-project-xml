package com.papero.capstoneexpert

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CoreApplication: MultiDexApplication() {

    @Inject
    lateinit var bar: Application
    override fun onCreate() {
        super.onCreate()
    }

//    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//
//    }
//
//    override fun onActivityStarted(activity: Activity) {
//
//    }
//
//    override fun onActivityResumed(activity: Activity) {
//
//    }
//
//    override fun onActivityPaused(activity: Activity) {
//
//    }
//
//    override fun onActivityStopped(activity: Activity) {
//
//    }
//
//    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//
//    }
//
//    override fun onActivityDestroyed(activity: Activity) {
//
//    }
}
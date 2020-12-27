package com.example.secureroomdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.sqlcipher.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class RoomApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree());


    }
}
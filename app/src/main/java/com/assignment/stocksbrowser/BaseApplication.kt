package com.assignment.stocksbrowser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.orbitmvi.orbit.viewmodel.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

package com.link2me

import android.app.Application
import timber.log.Timber

class Link2meApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("link2me application started")
    }
}

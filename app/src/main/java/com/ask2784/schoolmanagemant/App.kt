package com.ask2784.schoolmanagemant

import android.app.Application
import com.google.android.material.color.DynamicColors

class App : Application() {
    override fun onCreate() {
        super.onCreate()
         DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
package com.haghpanah.pienote

import android.app.Application
import com.haghpanah.pienote.feature.home.di.homeModule
import org.koin.core.context.startKoin

class PienoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            homeModule
        }
    }
}
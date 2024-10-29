package com.haghpanah.pienote

import android.app.Application
import com.haghpanah.pienote.di.commonModule
import com.haghpanah.pienote.di.databaseModule
import com.haghpanah.pienote.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PienoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PienoteApplication)
            modules(
                databaseModule,
                commonModule,
                homeModule
            )
        }
    }
}
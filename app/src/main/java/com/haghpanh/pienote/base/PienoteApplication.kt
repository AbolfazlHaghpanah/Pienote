package com.haghpanh.pienote.base

import android.app.Application
import com.haghpanh.pienote.commondi.commonModule
import com.haghpanh.pienote.commondi.daoModule
import com.haghpanh.pienote.features.category.di.categoryModule
import com.haghpanh.pienote.features.home.di.homeModule
import com.haghpanh.pienote.features.note.di.noteModule
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
                commonModule,
                daoModule,
                homeModule,
                categoryModule,
                noteModule
            )
        }
    }
}

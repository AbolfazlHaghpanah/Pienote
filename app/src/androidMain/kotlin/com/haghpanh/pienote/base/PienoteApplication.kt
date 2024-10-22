package pienote.base

import android.app.Application
import pienote.commondi.commonModule
import pienote.commondi.daoModule
import pienote.features.category.di.categoryModule
import pienote.features.home.di.homeModule
import pienote.features.note.di.noteModule
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

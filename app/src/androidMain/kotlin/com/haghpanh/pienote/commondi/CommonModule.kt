package pienote.commondi

import androidx.room.Room
import pienote.commondomain.repository.CommonRepository
import pienote.commondomain.usecase.SaveImageUriInCacheUseCase
import pienote.commonui.utils.SnackbarManager
import pienote.data.repository.CommonRepositoryImpl
import pienote.data.utils.PienoteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single<CommonRepository> {
        CommonRepositoryImpl(get(), get())
    }

    single { SaveImageUriInCacheUseCase(androidContext()) }

    single { SnackbarManager(androidContext()) }

    single<PienoteDatabase> {
        Room.databaseBuilder(androidContext(), PienoteDatabase::class.java, "pienote-db")
            .build()
    }
}

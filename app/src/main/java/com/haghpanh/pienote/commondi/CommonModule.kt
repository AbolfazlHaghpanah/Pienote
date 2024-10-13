package com.haghpanh.pienote.commondi

import androidx.room.Room
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.commondomain.usecase.SaveImageUriInCacheUseCase
import com.haghpanh.pienote.commonui.utils.SnackbarManager
import com.haghpanh.pienote.data.repository.CommonRepositoryImpl
import com.haghpanh.pienote.data.utils.PienoteDatabase
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

package com.haghpanah.pienote.di

import PienoteDatabaseDriverFactory
import com.haghpanah.pienote.database.PienoteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<PienoteDatabase> {
        PienoteDatabase(PienoteDatabaseDriverFactory(androidContext()).createDriver())
    }
}
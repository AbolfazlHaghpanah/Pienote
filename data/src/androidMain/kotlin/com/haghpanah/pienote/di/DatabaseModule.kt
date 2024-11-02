package com.haghpanah.pienote.di

import PienoteDatabaseDriverFactory
import app.cash.sqldelight.db.SqlDriver
import com.haghpanah.pienote.database.PienoteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> {
        PienoteDatabaseDriverFactory(androidContext()).createDriver()
    }
    single<PienoteDatabase> {
        PienoteDatabase(get())
    }
}
package com.haghpanah.pienote.di

import PienoteDatabaseDriverFactory
import app.cash.sqldelight.db.SqlDriver
import com.haghpanah.pienote.database.PienoteDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> {
        PienoteDatabaseDriverFactory().createDriver()
    }
    single<PienoteDatabase> {
        PienoteDatabase(get())
    }
}
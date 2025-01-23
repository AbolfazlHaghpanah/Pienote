package com.haghpanah.pienote.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.haghpanah.pienote.database.PienoteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = PienoteDatabase.Schema,
            context = androidContext(),
            name = "pienote.db",
            callback = object : AndroidSqliteDriver.Callback(PienoteDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }
    single<PienoteDatabase> {
        PienoteDatabase(get())
    }
}
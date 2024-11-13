package com.haghpanah.pienote.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.haghpanah.pienote.database.PienoteDatabase
import org.koin.dsl.module
import java.util.Properties

val databaseModule = module {
    single<SqlDriver> {
        val driver: SqlDriver = JdbcSqliteDriver(
            url = "jdbc:sqlite:pienote.db",
            properties = Properties().apply { put("foreign_keys", "true") }
        )
        PienoteDatabase.Schema.create(driver)
        driver
    }

    single<PienoteDatabase> {
        PienoteDatabase(get())
    }
}

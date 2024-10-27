import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import database.PienoteDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual class PienoteDatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PienoteDatabase.Schema, context, "test.db")
    }
}

val databaseDriverModule = module {
    singleOf(::PienoteDatabaseDriverFactory)
}
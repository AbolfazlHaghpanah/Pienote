import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.haghpanah.pienote.database.PienoteDatabase
import java.util.Properties

actual class PienoteDatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(
            url = JdbcSqliteDriver.IN_MEMORY,
            properties = Properties().apply { put("foreign_keys", "true") }
        )
        PienoteDatabase.Schema.create(driver)
        return driver
    }
}
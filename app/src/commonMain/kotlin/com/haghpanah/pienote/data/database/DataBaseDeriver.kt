import app.cash.sqldelight.db.SqlDriver

expect class PienoteDatabaseDriverFactory {
    fun createDriver(): SqlDriver
}


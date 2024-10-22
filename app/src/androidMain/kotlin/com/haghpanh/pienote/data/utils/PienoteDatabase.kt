package pienote.data.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import pienote.data.dao.CategoryDao
import pienote.data.dao.NoteDao
import pienote.data.entity.CategoryEntity
import pienote.data.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    views = []
)
abstract class PienoteDatabase : RoomDatabase() {
    abstract fun NoteDao(): NoteDao
    abstract fun CategoryDao(): CategoryDao
}

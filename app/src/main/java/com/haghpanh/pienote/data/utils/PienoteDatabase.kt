package com.haghpanh.pienote.data.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.entity.CategoryEntity
import com.haghpanh.pienote.data.entity.NoteEntity

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

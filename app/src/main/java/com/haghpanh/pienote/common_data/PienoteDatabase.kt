package com.haghpanh.pienote.common_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import com.haghpanh.pienote.feature_home.data.dao.HomeDao
import com.haghpanh.pienote.feature_note.data.dao.NoteDao

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class
    ],
    version = 1,
    views = []
)
abstract class PienoteDatabase : RoomDatabase() {
    abstract fun HomeDao(): HomeDao
    abstract fun NoteDao(): NoteDao
    abstract fun CategoryDao(): CategoryDao
}
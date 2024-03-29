package com.haghpanh.pienote.commondata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.home.data.dao.HomeDao

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class
    ],
    version = 1
)
abstract class PienoteDatabase : RoomDatabase() {
    abstract fun HomeDao(): HomeDao
}
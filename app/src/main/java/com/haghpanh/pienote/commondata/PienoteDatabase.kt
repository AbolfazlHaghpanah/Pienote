package com.haghpanh.pienote.commondata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haghpanh.pienote.commondata.dao.CommonDao
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.features.category.data.dao.CategoryDao
import com.haghpanh.pienote.features.favorite.data.dao.FavoriteDao
import com.haghpanh.pienote.features.favorite.data.entity.FavoriteNotesEntity
import com.haghpanh.pienote.features.home.data.dao.HomeDao
import com.haghpanh.pienote.features.note.data.dao.NoteDao
import com.haghpanh.pienote.features.noteslist.data.dao.NotesListDao

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        FavoriteNotesEntity::class
    ],
    version = 1,
    views = []
)
abstract class PienoteDatabase : RoomDatabase() {
    abstract fun HomeDao(): HomeDao
    abstract fun NoteDao(): NoteDao
    abstract fun CategoryDao(): CategoryDao
    abstract fun FavoriteNotesDao(): FavoriteDao
    abstract fun NotesListDao(): NotesListDao
    abstract fun CommonDao(): CommonDao
}
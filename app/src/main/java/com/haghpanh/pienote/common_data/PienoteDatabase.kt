package com.haghpanh.pienote.common_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haghpanh.pienote.common_data.dao.CommonDao
import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import com.haghpanh.pienote.feature_favorite.data.dao.FavoriteDao
import com.haghpanh.pienote.feature_favorite.data.entity.FavoriteNotesEntity
import com.haghpanh.pienote.feature_home.data.dao.HomeDao
import com.haghpanh.pienote.feature_note.data.dao.NoteDao
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesListDao

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
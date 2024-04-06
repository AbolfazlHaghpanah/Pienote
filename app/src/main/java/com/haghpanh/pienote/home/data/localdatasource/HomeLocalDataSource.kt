package com.haghpanh.pienote.home.data.localdatasource

import androidx.room.Delete
import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeNotes(): Flow<List<NoteEntity>>
    fun observeCategories(): Flow<List<CategoryEntity>>
    fun observeNoteByCategory(categoryId: Int): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun deleteNote(note : NoteEntity)
}
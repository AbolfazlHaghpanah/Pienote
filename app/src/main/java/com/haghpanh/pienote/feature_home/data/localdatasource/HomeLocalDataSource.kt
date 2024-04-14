package com.haghpanh.pienote.feature_home.data.localdatasource

import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeNotes(): Flow<List<NoteEntity>>
    fun observeCategories(): Flow<List<CategoryEntity>>
    fun observeNoteByCategory(categoryId: Int): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun deleteNote(note : NoteEntity)
}
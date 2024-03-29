package com.haghpanh.pienote.home.data.localdatasource

import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.commondata.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeNotes(): Flow<List<NoteEntity>>
    fun observeCategories(): Flow<List<CategoryEntity>>
    fun observeNoteByCategory(categoryId: Int): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
    suspend fun insertCategory(category: CategoryEntity)
}
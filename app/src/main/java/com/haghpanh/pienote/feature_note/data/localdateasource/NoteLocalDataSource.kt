package com.haghpanh.pienote.feature_note.data.localdateasource

import com.haghpanh.pienote.common_data.entity.CategoryEntity
import com.haghpanh.pienote.common_data.entity.NoteEntity
import com.haghpanh.pienote.feature_note.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun observeNote(id: Int): Flow<NoteWithCategory>
    fun getCategories(): List<CategoryEntity>
    suspend fun insertNote(note : NoteEntity)
    suspend fun updateNote(note: NoteEntity)
}

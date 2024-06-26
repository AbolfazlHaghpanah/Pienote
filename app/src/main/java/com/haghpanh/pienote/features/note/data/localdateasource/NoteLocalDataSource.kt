package com.haghpanh.pienote.features.note.data.localdateasource

import com.haghpanh.pienote.commondata.entity.CategoryEntity
import com.haghpanh.pienote.commondata.entity.NoteEntity
import com.haghpanh.pienote.features.note.data.relation.NoteWithCategory
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun observeNote(id: Int): Flow<NoteWithCategory>
    fun getCategories(): List<CategoryEntity>
    suspend fun insertNote(note : NoteEntity)
    suspend fun updateNote(note: NoteEntity)
}

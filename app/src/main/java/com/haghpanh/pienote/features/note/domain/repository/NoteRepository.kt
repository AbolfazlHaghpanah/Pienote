package com.haghpanh.pienote.features.note.domain.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.note.domain.model.NoteWithCategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNote(id: Int): Flow<NoteWithCategoryDomainModel>
    suspend fun getCategories(): List<CategoryDomainModel>
    suspend fun insertNote(note: NoteDomainModel): Int
    suspend fun updateNote(note: NoteDomainModel)
}

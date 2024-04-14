package com.haghpanh.pienote.feature_note.domain.repository

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_note.domain.model.NoteWithCategoryDomainModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNote(id: Int): Flow<NoteWithCategoryDomainModel>
    fun getCategories(): List<CategoryDomainModel>
    suspend fun insertNote(note: NoteDomainModel)
    suspend fun updateNote(note: NoteDomainModel)
}
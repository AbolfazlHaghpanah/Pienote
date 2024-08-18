package com.haghpanh.pienote.features.home.domain.repository

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.domain.model.CategoryWithNotesCountDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes(): Flow<List<NoteDomainModel>>
    fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>>
    fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>>
    suspend fun deleteNote(note: NoteDomainModel)
}

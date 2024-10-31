package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes(): Flow<List<NoteDomainModel>>
    fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>>
    fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>>
    suspend fun deleteNote(note: NoteDomainModel)
}

package pienote.features.home.domain.repository

import pienote.commondomain.model.NoteDomainModel
import pienote.features.home.domain.model.CategoryWithNotesCountDomainModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun observeNotes(): Flow<List<NoteDomainModel>>
    fun observeCategories(): Flow<List<CategoryWithNotesCountDomainModel>>
    fun observeNotesByCategory(categoryId: Int): Flow<List<NoteDomainModel>>
    suspend fun deleteNote(note: NoteDomainModel)
}

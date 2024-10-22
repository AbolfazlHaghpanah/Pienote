package pienote.features.category.domain.repository

import pienote.commondomain.model.CategoryDomainModel
import pienote.commondomain.model.NoteDomainModel
import pienote.features.category.domain.model.CategoryWithNotesDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategory(id: Int): Flow<CategoryWithNotesDomainModel>
    fun observeAvailableNotes(): Flow<List<NoteDomainModel>>
    suspend fun deleteNoteFromCategory(noteId: Int)
    suspend fun updateCategory(categoryDomainModel: CategoryDomainModel)
    suspend fun addNoteToCategory(noteId: Int, categoryId: Int)
}

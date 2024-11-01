package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.CategoryWithNotesDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategory(id: Long): Flow<CategoryWithNotesDomainModel>
    fun observeAvailableNotes(): Flow<List<NoteDomainModel>>
    suspend fun deleteNoteFromCategory(noteId: Long)
    suspend fun updateCategory(categoryDomainModel: CategoryDomainModel)
    suspend fun addNoteToCategory(noteId: Long, categoryId: Long)
}

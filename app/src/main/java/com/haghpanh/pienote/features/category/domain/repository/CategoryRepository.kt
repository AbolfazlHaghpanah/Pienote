package com.haghpanh.pienote.features.category.domain.repository

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.category.domain.model.CategoryWithNotesDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategory(id: Int): Flow<CategoryWithNotesDomainModel>
    fun observeAvailableNotes(): Flow<List<NoteDomainModel>>
    suspend fun deleteNoteFromCategory(noteId: Int)
    suspend fun updateCategory(categoryDomainModel: CategoryDomainModel)
    suspend fun addNoteToCategory(noteId: Int, categoryId: Int)
}

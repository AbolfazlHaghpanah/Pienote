package com.haghpanh.pienote.feature_category.domain.repository

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_category.domain.model.CategoryWithNotesDomainModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategory(id: Int): Flow<CategoryWithNotesDomainModel>
    suspend fun deleteNoteFromCategory(noteId : Int)
    suspend fun updateCategory(categoryDomainModel: CategoryDomainModel)
}
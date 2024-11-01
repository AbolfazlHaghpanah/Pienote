package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.model.CategoryWithNotesDomainModel
import com.haghpanah.pienote.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryObserveCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(id: Long): Flow<CategoryWithNotesDomainModel> =
        categoryRepository.observeCategory(id)
}
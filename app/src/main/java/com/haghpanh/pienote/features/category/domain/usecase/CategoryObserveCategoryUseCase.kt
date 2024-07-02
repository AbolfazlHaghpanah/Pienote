package com.haghpanh.pienote.features.category.domain.usecase

import com.haghpanh.pienote.features.category.domain.model.CategoryWithNotesDomainModel
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryObserveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(id: Int): Flow<CategoryWithNotesDomainModel> =
        categoryRepository.observeCategory(id)
}

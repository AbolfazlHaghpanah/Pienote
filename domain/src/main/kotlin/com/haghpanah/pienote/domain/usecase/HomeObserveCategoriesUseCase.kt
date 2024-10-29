package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveCategoriesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<CategoryWithNotesCountDomainModel>> =
        homeRepository.observeCategories()
}
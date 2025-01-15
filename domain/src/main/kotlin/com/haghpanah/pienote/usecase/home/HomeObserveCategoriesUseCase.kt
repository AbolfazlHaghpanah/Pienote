package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.model.CategoryWithNotesCountDomainModel
import com.haghpanah.pienote.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveCategoriesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<CategoryWithNotesCountDomainModel>> =
        homeRepository.observeCategories()
}




















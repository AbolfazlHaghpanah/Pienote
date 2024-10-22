package pienote.features.home.domain.usecase

import pienote.features.home.domain.model.CategoryWithNotesCountDomainModel
import pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveCategoriesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<CategoryWithNotesCountDomainModel>> =
        homeRepository.observeCategories()
}

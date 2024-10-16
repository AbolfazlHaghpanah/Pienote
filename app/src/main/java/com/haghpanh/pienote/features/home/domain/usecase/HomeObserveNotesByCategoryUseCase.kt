package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesByCategoryUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotesByCategory(categoryId)
}

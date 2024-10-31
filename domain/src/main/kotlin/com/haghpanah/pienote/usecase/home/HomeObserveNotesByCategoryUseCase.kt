package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesByCategoryUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotesByCategory(categoryId)
}
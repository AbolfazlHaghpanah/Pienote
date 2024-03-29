package com.haghpanh.pienote.home.domain.usecase

import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeObserveNotesByCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotesByCategory(categoryId)
}
package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.repository.HomeRepository

class HomeObserveNotesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() = homeRepository.observeNotes()
}
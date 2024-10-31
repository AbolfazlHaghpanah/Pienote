package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.repository.HomeRepository

class HomeObserveNotesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() = homeRepository.observeNotes()
}
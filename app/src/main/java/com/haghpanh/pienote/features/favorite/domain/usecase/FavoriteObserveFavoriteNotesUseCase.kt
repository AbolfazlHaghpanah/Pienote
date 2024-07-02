package com.haghpanh.pienote.features.favorite.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteObserveFavoriteNotesUseCase @Inject constructor(
    private var favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<NoteDomainModel>> {
        return favoriteRepository.observeFavoriteNotes()
    }
}

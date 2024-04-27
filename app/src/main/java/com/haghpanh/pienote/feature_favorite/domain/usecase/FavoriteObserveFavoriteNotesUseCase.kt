package com.haghpanh.pienote.feature_favorite.domain.usecase

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_favorite.data.entity.FavoriteNotesEntity
import com.haghpanh.pienote.feature_favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteObserveFavoriteNotesUseCase @Inject constructor(
    private var favoriteRepository: FavoriteRepository
) {
    operator fun invoke () : Flow<List<NoteDomainModel>> {
        return favoriteRepository.observeFavoriteNotes()
    }
}
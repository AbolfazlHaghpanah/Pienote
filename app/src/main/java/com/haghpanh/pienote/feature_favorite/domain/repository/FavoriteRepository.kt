package com.haghpanh.pienote.feature_favorite.domain.repository

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_favorite.data.entity.FavoriteNotesEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavoriteNotes() : Flow<List<NoteDomainModel>>
}
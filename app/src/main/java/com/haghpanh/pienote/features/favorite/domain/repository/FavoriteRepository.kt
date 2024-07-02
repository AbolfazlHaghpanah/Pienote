package com.haghpanh.pienote.features.favorite.domain.repository

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavoriteNotes(): Flow<List<NoteDomainModel>>
}

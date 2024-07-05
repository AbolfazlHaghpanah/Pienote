package com.haghpanh.pienote.features.favorite.data.repository

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.favorite.data.dao.FavoriteDao
import com.haghpanh.pienote.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    override fun observeFavoriteNotes(): Flow<List<NoteDomainModel>> =
        favoriteDao.observeFavoriteNotes().map { it.map { it.toDomainModel() } }
}

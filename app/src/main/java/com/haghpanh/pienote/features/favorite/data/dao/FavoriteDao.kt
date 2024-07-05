package com.haghpanh.pienote.features.favorite.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.haghpanh.pienote.features.favorite.data.entity.FavoriteNotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {





    @Query("SELECT * FROM FAVORITE_NOTES")
    fun observeFavoriteNotes(): Flow<List<FavoriteNotesEntity>>
}

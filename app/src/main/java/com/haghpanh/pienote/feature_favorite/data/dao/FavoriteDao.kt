package com.haghpanh.pienote.feature_favorite.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.haghpanh.pienote.feature_favorite.data.entity.FavoriteNotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FAVORITE_NOTES")
    fun observeFavoriteNotes () : Flow<List<FavoriteNotesEntity>>
}
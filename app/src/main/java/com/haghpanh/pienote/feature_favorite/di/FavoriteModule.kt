package com.haghpanh.pienote.feature_favorite.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.feature_favorite.data.dao.FavoriteDao
import com.haghpanh.pienote.feature_favorite.data.repository.FavoriteRepositoryImpl
import com.haghpanh.pienote.feature_favorite.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteModule {

    @Binds
    abstract fun bindeFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

    companion object {
        @Provides
        fun provideFavoriteDao(pienoteDatabase: PienoteDatabase): FavoriteDao =
            pienoteDatabase.FavoriteNotesDao()
    }
}
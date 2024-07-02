package com.haghpanh.pienote.features.favorite.di

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.features.favorite.data.dao.FavoriteDao
import com.haghpanh.pienote.features.favorite.data.repository.FavoriteRepositoryImpl
import com.haghpanh.pienote.features.favorite.domain.repository.FavoriteRepository
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
            pienoteDatabase.FavoriteDao()
    }
}

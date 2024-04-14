package com.haghpanh.pienote.feature_home.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.feature_home.data.dao.HomeDao
import com.haghpanh.pienote.feature_home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.feature_home.data.localdatasource.HomeLocalDataSourceImpl
import com.haghpanh.pienote.feature_home.data.repository.HomeRepositoryImpl
import com.haghpanh.pienote.feature_home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindsHomeLocalDataSource(
        homeLocalDataSourceImpl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource

    @Binds
    abstract fun bindsHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    companion object {
        @Provides
        fun homeDaoProvider(pienoteDatabase: PienoteDatabase): HomeDao {
            return pienoteDatabase.HomeDao()
        }
    }
}
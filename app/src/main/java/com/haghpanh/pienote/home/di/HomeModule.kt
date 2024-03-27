package com.haghpanh.pienote.home.di

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.home.data.dao.HomeDao
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSource
import com.haghpanh.pienote.home.data.localdatasource.HomeLocalDataSourceImpl
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

    companion object {
        @Provides
        fun homeDaoProvider(pienoteDatabase: PienoteDatabase): HomeDao {
            return pienoteDatabase.HomeDao()
        }
    }
}
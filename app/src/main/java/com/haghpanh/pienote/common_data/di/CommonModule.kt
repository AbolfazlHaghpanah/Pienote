package com.haghpanh.pienote.common_data.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.common_data.dao.CommonDao
import com.haghpanh.pienote.common_data.repository.CommonRepositoryImpl
import com.haghpanh.pienote.common_domain.repository.CommonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    abstract fun bindsCommonRepository(
        commonRepositoryImpl: CommonRepositoryImpl
    ): CommonRepository


    companion object {
        @Provides
        fun provideCommonDao(database: PienoteDatabase): CommonDao =
            database.CommonDao()
    }
}
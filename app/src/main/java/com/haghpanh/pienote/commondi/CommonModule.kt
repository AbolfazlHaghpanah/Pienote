package com.haghpanh.pienote.commondi

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.commondata.dao.CommonDao
import com.haghpanh.pienote.commondata.repository.CommonRepositoryImpl
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.commonui.utils.SnackbarManager
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

        @Provides
        fun provideSnackbarManager() = SnackbarManager()
    }
}

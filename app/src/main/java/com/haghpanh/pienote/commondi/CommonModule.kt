package com.haghpanh.pienote.commondi

import android.content.Context
import androidx.room.Room
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.data.repository.CommonRepositoryImpl
import com.haghpanh.pienote.data.utils.PienoteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {
    @Binds
    abstract fun bindsCommonRepository(
        commonRepositoryImpl: CommonRepositoryImpl
    ): CommonRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): PienoteDatabase {
            return Room.databaseBuilder(context, PienoteDatabase::class.java, "pienote-db")
                .build()
        }
    }
}

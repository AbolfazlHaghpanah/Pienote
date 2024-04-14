package com.haghpanh.pienote.feature_category.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.feature_category.data.dao.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @Provides
    fun provideCategoryDao(pienoteDatabase: PienoteDatabase): CategoryDao {
        return pienoteDatabase.CategoryDao()
    }
}
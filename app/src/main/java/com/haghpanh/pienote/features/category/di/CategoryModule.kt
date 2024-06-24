package com.haghpanh.pienote.features.category.di

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.features.category.data.dao.CategoryDao
import com.haghpanh.pienote.features.category.data.repository.CategoryRepositoryImpl
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Binds
    abstract fun bindsCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    companion object {
        @Provides
        fun provideCategoryDao(pienoteDatabase: PienoteDatabase): CategoryDao {
            return pienoteDatabase.CategoryDao()
        }
    }
}
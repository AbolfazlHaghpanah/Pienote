package com.haghpanh.pienote.commondi

import com.haghpanh.pienote.data.dao.CategoryDao
import com.haghpanh.pienote.data.dao.NoteDao
import com.haghpanh.pienote.data.utils.PienoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideCategoryDao(pienoteDatabase: PienoteDatabase): CategoryDao =
        pienoteDatabase.CategoryDao()

    @Provides
    fun provideNoteDao(pienoteDatabase: PienoteDatabase): NoteDao =
        pienoteDatabase.NoteDao()
}

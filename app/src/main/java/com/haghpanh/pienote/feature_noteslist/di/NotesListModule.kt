package com.haghpanh.pienote.feature_noteslist.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.feature_noteslist.data.dao.NotesListDao
import com.haghpanh.pienote.feature_noteslist.data.repository.NotesListRepositoryImpl
import com.haghpanh.pienote.feature_noteslist.domain.repository.NotesListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NotesListModule {
    @Binds
    abstract fun bindsNotesListRepository(
        notesListRepositoryImpl: NotesListRepositoryImpl
    ): NotesListRepository

    companion object {
        @Provides
        fun provideNoteDao(pienoteDatabase: PienoteDatabase): NotesListDao =
            pienoteDatabase.NotesListDao()
    }
}
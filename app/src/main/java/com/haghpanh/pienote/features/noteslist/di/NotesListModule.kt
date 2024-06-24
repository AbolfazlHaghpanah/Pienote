package com.haghpanh.pienote.features.noteslist.di

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.features.noteslist.data.dao.NotesListDao
import com.haghpanh.pienote.features.noteslist.data.repository.NotesListRepositoryImpl
import com.haghpanh.pienote.features.noteslist.domain.repository.NotesListRepository
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
package com.haghpanh.pienote.feature_note.di

import com.haghpanh.pienote.common_data.PienoteDatabase
import com.haghpanh.pienote.feature_note.data.dao.NoteDao
import com.haghpanh.pienote.feature_note.data.localdateasource.NoteLocalDataSource
import com.haghpanh.pienote.feature_note.data.localdateasource.NoteLocalDataSourceImpl
import com.haghpanh.pienote.feature_note.data.repository.NoteRepositoryImpl
import com.haghpanh.pienote.feature_note.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {
    @Binds
    abstract fun bindsNoteLocalDataSource(noteLocalDataSourceImpl: NoteLocalDataSourceImpl): NoteLocalDataSource

    @Binds
    abstract fun bindsNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

    companion object {
        @Provides
        fun provideNoteDao(pienoteDatabase: PienoteDatabase): NoteDao =
            pienoteDatabase.NoteDao()
    }
}
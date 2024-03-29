package com.haghpanh.pienote.note.di

import com.haghpanh.pienote.commondata.PienoteDatabase
import com.haghpanh.pienote.note.data.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {
    companion object {
        @Provides
        fun provideNoteDao(pienoteDatabase: PienoteDatabase): NoteDao =
            pienoteDatabase.NoteDao()
    }
}
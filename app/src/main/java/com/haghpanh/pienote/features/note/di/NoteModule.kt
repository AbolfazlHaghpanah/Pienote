package com.haghpanh.pienote.features.note.di

import com.haghpanh.pienote.data.repository.NoteRepositoryImpl
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {
    @Binds
    abstract fun bindsNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}

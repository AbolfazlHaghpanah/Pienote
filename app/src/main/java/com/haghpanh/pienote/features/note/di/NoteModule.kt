package com.haghpanh.pienote.features.note.di

import com.haghpanh.pienote.data.repository.NoteRepositoryImpl
import com.haghpanh.pienote.features.note.domain.repository.NoteRepository
import com.haghpanh.pienote.features.note.domain.usecase.NoteGetCategoriesUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteInsertNoteUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteObserveNoteInfoUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteUpdateNoteImageUseCase
import com.haghpanh.pienote.features.note.domain.usecase.NoteUpdateNoteUseCase
import com.haghpanh.pienote.features.note.ui.NoteViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val noteModule = module {
    factoryOf(::NoteRepositoryImpl) bind NoteRepository::class

    factoryOf(::NoteGetCategoriesUseCase)
    factoryOf(::NoteInsertNoteUseCase)
    factoryOf(::NoteUpdateNoteUseCase)
    factoryOf(::NoteObserveNoteInfoUseCase)
    factoryOf(::NoteUpdateNoteImageUseCase)
    factoryOf(::NoteUpdateNoteUseCase)

    viewModelOf(::NoteViewModel)
}

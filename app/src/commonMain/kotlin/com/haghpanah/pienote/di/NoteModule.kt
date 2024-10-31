package com.haghpanah.pienote.di

import com.haghpanah.pienote.feature.note.NoteViewModel
import com.haghpanah.pienote.usecase.note.NoteGetCategoriesUseCase
import com.haghpanah.pienote.usecase.note.NoteInsertNoteUseCase
import com.haghpanah.pienote.usecase.note.NoteObserveNoteInfoUseCase
import com.haghpanah.pienote.usecase.note.NoteUpdateNoteImageUseCase
import com.haghpanah.pienote.usecase.note.NoteUpdateNoteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val noteModule = module {
    factoryOf(::NoteGetCategoriesUseCase)
    factoryOf(::NoteInsertNoteUseCase)
    factoryOf(::NoteUpdateNoteUseCase)
    factoryOf(::NoteObserveNoteInfoUseCase)
    factoryOf(::NoteUpdateNoteImageUseCase)
    factoryOf(::NoteUpdateNoteUseCase)

    viewModelOf(::NoteViewModel)
}

package pienote.features.note.di

import pienote.data.repository.NoteRepositoryImpl
import pienote.features.note.domain.repository.NoteRepository
import pienote.features.note.domain.usecase.NoteGetCategoriesUseCase
import pienote.features.note.domain.usecase.NoteInsertNoteUseCase
import pienote.features.note.domain.usecase.NoteObserveNoteInfoUseCase
import pienote.features.note.domain.usecase.NoteUpdateNoteImageUseCase
import pienote.features.note.domain.usecase.NoteUpdateNoteUseCase
import pienote.features.note.ui.NoteViewModel
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

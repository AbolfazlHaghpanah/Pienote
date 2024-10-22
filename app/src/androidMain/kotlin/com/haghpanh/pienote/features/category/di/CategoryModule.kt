package pienote.features.category.di

import pienote.data.repository.CategoryRepositoryImpl
import pienote.features.category.domain.repository.CategoryRepository
import pienote.features.category.domain.usecase.CategoryAddNoteToCategoryUseCase
import pienote.features.category.domain.usecase.CategoryDeleteNoteFromCategoryUseCase
import pienote.features.category.domain.usecase.CategoryObserveAvailableNotesUseCase
import pienote.features.category.domain.usecase.CategoryObserveCategoryUseCase
import pienote.features.category.domain.usecase.CategoryUpdateCategoryUseCase
import pienote.features.category.domain.usecase.CategoryUpdateImageUseCase
import pienote.features.category.ui.CategoryViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val categoryModule = module {
    factoryOf(::CategoryRepositoryImpl) bind CategoryRepository::class

    factoryOf(::CategoryAddNoteToCategoryUseCase)
    factoryOf(::CategoryDeleteNoteFromCategoryUseCase)
    factoryOf(::CategoryObserveAvailableNotesUseCase)
    factoryOf(::CategoryObserveCategoryUseCase)
    factoryOf(::CategoryUpdateCategoryUseCase)
    factoryOf(::CategoryUpdateImageUseCase)

    viewModelOf(::CategoryViewModel)
}

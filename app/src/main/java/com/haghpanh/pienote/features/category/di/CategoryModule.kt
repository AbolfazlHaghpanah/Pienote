package com.haghpanh.pienote.features.category.di

import com.haghpanh.pienote.data.repository.CategoryRepositoryImpl
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository
import com.haghpanh.pienote.features.category.domain.usecase.CategoryAddNoteToCategoryUseCase
import com.haghpanh.pienote.features.category.domain.usecase.CategoryDeleteNoteFromCategoryUseCase
import com.haghpanh.pienote.features.category.domain.usecase.CategoryObserveAvailableNotesUseCase
import com.haghpanh.pienote.features.category.domain.usecase.CategoryObserveCategoryUseCase
import com.haghpanh.pienote.features.category.domain.usecase.CategoryUpdateCategoryUseCase
import com.haghpanh.pienote.features.category.domain.usecase.CategoryUpdateImageUseCase
import com.haghpanh.pienote.features.category.ui.CategoryViewModel
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

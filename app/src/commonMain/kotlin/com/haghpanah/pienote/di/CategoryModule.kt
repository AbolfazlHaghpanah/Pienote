package com.haghpanah.pienote.di

import com.haghpanah.pienote.category.CategoryViewModel
import com.haghpanah.pienote.usecase.category.CategoryAddNoteToCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryDeleteNoteFromCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryObserveAvailableNotesUseCase
import com.haghpanah.pienote.usecase.category.CategoryObserveCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryUpdateCategoryUseCase
import com.haghpanah.pienote.usecase.category.CategoryUpdateImageUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoryModule = module {
    factoryOf(::CategoryAddNoteToCategoryUseCase)
    factoryOf(::CategoryDeleteNoteFromCategoryUseCase)
    factoryOf(::CategoryObserveAvailableNotesUseCase)
    factoryOf(::CategoryObserveCategoryUseCase)
    factoryOf(::CategoryUpdateCategoryUseCase)
    factoryOf(::CategoryUpdateImageUseCase)

    viewModelOf(::CategoryViewModel)
}
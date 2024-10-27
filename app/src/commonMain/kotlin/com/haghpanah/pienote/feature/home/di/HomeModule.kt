package com.haghpanah.pienote.feature.home.di

import com.haghpanah.pienote.feature.home.ui.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
//    factoryOf(::HomeRepositoryImpl) bind HomeRepository::class
//
//    factoryOf(::HomeAddNotesToCategoryUseCase)
//    factoryOf(::HomeDeleteNoteUseCase)
//    factoryOf(::HomeInsertCategoryUseCase)
//    factoryOf(::HomeObserveCategoriesUseCase)
//    factoryOf(::HomeObserveNotesByCategoryUseCase)
//    factoryOf(::HomeObserveNotesUseCase)

    viewModelOf(::HomeViewModel)
}

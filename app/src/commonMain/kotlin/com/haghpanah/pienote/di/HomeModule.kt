package com.haghpanah.pienote.di

import com.haghpanah.pienote.home.HomeViewModel
import com.haghpanah.pienote.usecase.home.HomeAddNotesToCategoryUseCase
import com.haghpanah.pienote.usecase.home.HomeDeleteNoteUseCase
import com.haghpanah.pienote.usecase.home.HomeInsertCategoryUseCase
import com.haghpanah.pienote.usecase.home.HomeObserveCategoriesUseCase
import com.haghpanah.pienote.usecase.home.HomeObserveNotesByCategoryUseCase
import com.haghpanah.pienote.usecase.home.HomeObserveNotesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val homeModule = module {
    factoryOf(::HomeAddNotesToCategoryUseCase)
    factoryOf(::HomeDeleteNoteUseCase)
    factoryOf(::HomeInsertCategoryUseCase)
    factoryOf(::HomeObserveCategoriesUseCase)
    factoryOf(::HomeObserveNotesByCategoryUseCase)
    factoryOf(::HomeObserveNotesUseCase)

    viewModelOf(::HomeViewModel)
}
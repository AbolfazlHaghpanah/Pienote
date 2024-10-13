package com.haghpanh.pienote.features.home.di

import com.haghpanh.pienote.data.repository.HomeRepositoryImpl
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
import com.haghpanh.pienote.features.home.domain.usecase.HomeAddNotesToCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeDeleteNoteUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeInsertCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveCategoriesUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveNotesByCategoryUseCase
import com.haghpanh.pienote.features.home.domain.usecase.HomeObserveNotesUseCase
import com.haghpanh.pienote.features.home.ui.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::HomeRepositoryImpl) bind HomeRepository::class

    factoryOf(::HomeAddNotesToCategoryUseCase)
    factoryOf(::HomeDeleteNoteUseCase)
    factoryOf(::HomeInsertCategoryUseCase)
    factoryOf(::HomeObserveCategoriesUseCase)
    factoryOf(::HomeObserveNotesByCategoryUseCase)
    factoryOf(::HomeObserveNotesUseCase)

    viewModelOf(::HomeViewModel)
}

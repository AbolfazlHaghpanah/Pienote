package com.haghpanah.pienote.di

import com.haghpanah.pienote.domain.repository.HomeRepository
import com.haghpanah.pienote.domain.usecase.HomeAddNotesToCategoryUseCase
import com.haghpanah.pienote.domain.usecase.HomeDeleteNoteUseCase
import com.haghpanah.pienote.domain.usecase.HomeInsertCategoryUseCase
import com.haghpanah.pienote.domain.usecase.HomeObserveCategoriesUseCase
import com.haghpanah.pienote.domain.usecase.HomeObserveNotesByCategoryUseCase
import com.haghpanah.pienote.domain.usecase.HomeObserveNotesUseCase
import com.haghpanah.pienote.feature.home.HomeViewModel
import com.haghpanah.pienote.repository.HomeRepositoryImpl
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
package pienote.features.home.di

import pienote.data.repository.HomeRepositoryImpl
import pienote.features.home.domain.repository.HomeRepository
import pienote.features.home.domain.usecase.HomeAddNotesToCategoryUseCase
import pienote.features.home.domain.usecase.HomeDeleteNoteUseCase
import pienote.features.home.domain.usecase.HomeInsertCategoryUseCase
import pienote.features.home.domain.usecase.HomeObserveCategoriesUseCase
import pienote.features.home.domain.usecase.HomeObserveNotesByCategoryUseCase
import pienote.features.home.domain.usecase.HomeObserveNotesUseCase
import pienote.features.home.ui.HomeViewModel
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

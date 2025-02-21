package com.haghpanah.pienote.di

import com.haghpanah.pienote.repository.ConfigurationsRepository
import com.haghpanah.pienote.repository.ConfigurationsRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val platformSpecificRepositoryModule = module {
    factoryOf(::ConfigurationsRepositoryImpl) bind ConfigurationsRepository::class
}
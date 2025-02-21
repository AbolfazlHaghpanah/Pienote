package com.haghpanah.pienote.di

import com.haghpanah.pienote.repository.ConfigurationsRepository
import com.haghpanah.pienote.repository.ConfigurationsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val platformSpecificRepositoryModule = module {
    singleOf(::ConfigurationsRepositoryImpl) bind ConfigurationsRepository::class
}
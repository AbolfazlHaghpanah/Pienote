package com.haghpanah.pienote.di

import com.haghpanah.pienote.repository.CategoryRepository
import com.haghpanah.pienote.repository.CategoryRepositoryImpl
import com.haghpanah.pienote.repository.CommonRepository
import com.haghpanah.pienote.repository.CommonRepositoryImpl
import com.haghpanah.pienote.repository.HomeRepository
import com.haghpanah.pienote.repository.HomeRepositoryImpl
import com.haghpanah.pienote.repository.NoteRepository
import com.haghpanah.pienote.repository.NoteRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::HomeRepositoryImpl) bind HomeRepository::class
    factoryOf(::CommonRepositoryImpl) bind CommonRepository::class
    factoryOf(::NoteRepositoryImpl) bind NoteRepository::class
    factoryOf(::CategoryRepositoryImpl) bind CategoryRepository::class
}
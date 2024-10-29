package com.haghpanah.pienote.di

import com.haghpanah.pienote.domain.repository.CommonRepository
import com.haghpanah.pienote.repository.CommonRepositoryImpl
import org.koin.dsl.module

val commonModule = module {
    single<CommonRepository> {
        CommonRepositoryImpl(get())
    }
}
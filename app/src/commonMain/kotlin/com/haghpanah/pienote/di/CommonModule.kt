package com.haghpanah.pienote.di

import com.haghpanah.pienote.core.utlis.SnackbarManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::SnackbarManager)
}
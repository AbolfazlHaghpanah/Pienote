package com.haghpanah.pienote.di

import com.haghpanah.pienote.snackbar.SnackbarManager
import com.haghpanah.pienote.ui.MainViewModel
import com.haghpanah.pienote.usecase.configurations.ObserveThemeUseCase
import com.haghpanah.pienote.usecase.common.SaveImageUriInCacheUseCase
import com.haghpanah.pienote.usecase.configurations.SetThemeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::SnackbarManager)
    factoryOf(::SaveImageUriInCacheUseCase)
    factoryOf(::MainViewModel)
}
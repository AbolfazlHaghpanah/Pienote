package com.haghpanah.pienote.di

import com.haghpanah.pienote.ui.configurations.ConfigurationsViewModel
import com.haghpanah.pienote.usecase.configurations.ObserveCurrentLanguageUseCase
import com.haghpanah.pienote.usecase.configurations.ObserveThemeUseCase
import com.haghpanah.pienote.usecase.configurations.SetAppLanguageUseCase
import com.haghpanah.pienote.usecase.configurations.SetThemeUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val configurationsModule = module {
    singleOf(::SetThemeUseCase)
    singleOf(::ObserveThemeUseCase)
    singleOf(::ObserveCurrentLanguageUseCase)
    singleOf(::SetAppLanguageUseCase)
    viewModelOf(::ConfigurationsViewModel)
}
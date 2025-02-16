package com.haghpanah.pienote.di

import com.haghpanah.pienote.ui.configurations.home.ConfigurationsHomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val configurationsModule = module {
    viewModelOf(::ConfigurationsHomeViewModel)
}
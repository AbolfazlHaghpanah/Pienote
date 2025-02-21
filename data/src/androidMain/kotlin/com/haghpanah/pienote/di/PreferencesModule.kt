package com.haghpanah.pienote.di

import com.haghpanah.pienote.utils.PienotePreferences
import com.haghpanah.pienote.utils.PienotePreferencesImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val preferencesModule = module {
    singleOf(::PienotePreferencesImpl) bind PienotePreferences::class
}

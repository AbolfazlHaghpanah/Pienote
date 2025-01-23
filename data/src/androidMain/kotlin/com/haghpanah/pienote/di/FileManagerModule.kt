package com.haghpanah.pienote.di

import com.haghpanah.pienote.utils.FileManager
import com.haghpanah.pienote.utils.FileManagerImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val fileManagerModule = module {
    factoryOf(::FileManagerImpl) bind FileManager::class
}
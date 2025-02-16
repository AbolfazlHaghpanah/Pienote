package com.haghpanah.pienote.di

internal fun createPienoteModules() = listOf(
    homeModule,
    repositoryModule,
    databaseModule,
    noteModule,
    categoryModule,
    commonModule,
    fileManagerModule,
    preferencesModule,
    configurationsModule
)

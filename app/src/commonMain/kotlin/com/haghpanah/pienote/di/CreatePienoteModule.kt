package com.haghpanah.pienote.di

internal fun createPienoteModules() = listOf(
    homeModule,
    commonRepositoryModule,
    platformSpecificRepositoryModule,
    databaseModule,
    noteModule,
    categoryModule,
    commonModule,
    fileManagerModule,
    preferencesModule,
    configurationsModule,
)

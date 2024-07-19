package com.haghpanh.pienote.commondi

import android.content.Context
import androidx.room.Room
import com.haghpanh.pienote.commondata.PienoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PienoteDatabase {
        return Room.databaseBuilder(context, PienoteDatabase::class.java, "pienote-db")
            .build()
    }
}

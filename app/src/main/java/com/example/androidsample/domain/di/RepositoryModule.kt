package com.example.androidsample.domain.di

import WiseSayingDataStore
import android.app.Application
import com.example.androidsample.domain.repository.TodoRepository
import com.example.androidsample.domain.repository.WiseSayingDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideTodoRepository(application: Application): TodoRepository {
        return TodoRepository(application)
    }

    @Provides
    @Singleton
    fun provideWiseSayingRepository(
        application: Application): WiseSayingDataRepository {
        return WiseSayingDataRepository(application)
    }
}
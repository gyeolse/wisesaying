package com.seyefactory.wisespoon.domain.di

import android.app.Application
import com.seyefactory.wisespoon.domain.repository.TodoRepository
import com.seyefactory.wisespoon.domain.repository.WiseSayingDataRepository
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
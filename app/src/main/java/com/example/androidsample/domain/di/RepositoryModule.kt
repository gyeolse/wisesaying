package com.example.androidsample.domain.di

import android.app.Application
import com.example.androidsample.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideTodoRepository(application: Application): TodoRepository {
        return TodoRepository(application)
    }
}
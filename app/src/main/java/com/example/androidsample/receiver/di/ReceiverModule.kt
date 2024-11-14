package com.example.androidsample.receiver.di

import android.content.Context
import com.example.androidsample.data.datasource.database.WiseSayingDatabase
import com.example.androidsample.receiver.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReceiverModule {
    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmScheduler(context)
    }
}


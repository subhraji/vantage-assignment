package com.example.assignmentapp.di

import com.example.assignmentapp.data.remote.repository.task_manager.TaskManagerRepositoryImpl
import com.example.assignmentapp.data.remote.repository.weather.WeatherRepositoryImpl
import com.example.assignmentapp.domain.repository.task_manager.TaskManagerRepository
import com.example.assignmentapp.domain.repository.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideTaskManagerRepository(
        taskManagerRepositoryImpl: TaskManagerRepositoryImpl
    ): TaskManagerRepository = taskManagerRepositoryImpl

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository = weatherRepositoryImpl
}
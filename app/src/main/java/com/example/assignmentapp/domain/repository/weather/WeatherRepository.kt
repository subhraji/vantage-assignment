package com.example.assignmentapp.domain.repository.weather

import com.example.assignmentapp.data.remote.model.NetworkResource
import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import com.example.assignmentapp.domain.repository.BaseRepository

interface WeatherRepository : BaseRepository {
    suspend fun getWeatherData(
        cityName: String,
        apiKey: String
    ): NetworkResource<WeatherResponseModel>

}
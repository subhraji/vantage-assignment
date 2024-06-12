package com.example.assignmentapp.domain.usecase.weather

import com.example.assignmentapp.data.remote.model.NetworkResource
import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import com.example.assignmentapp.domain.repository.weather.WeatherRepository
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getWeatherData(cityName: String, apiKey: String): NetworkResource<WeatherResponseModel> {
        return weatherRepository.getWeatherData(cityName = cityName, apiKey = apiKey)
    }
}
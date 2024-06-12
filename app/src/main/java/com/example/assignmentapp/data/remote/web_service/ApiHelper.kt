package com.example.assignmentapp.data.remote.web_service

import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getWeather(
        cityName: String,
        apiKey: String
    ): Response<WeatherResponseModel>?
}
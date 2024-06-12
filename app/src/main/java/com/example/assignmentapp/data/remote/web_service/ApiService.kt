package com.example.assignmentapp.data.remote.web_service

import com.example.assignmentapp.BuildConfig
import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query(value = "q", encoded = true) cityName: String,
        @Query(value = "appid", encoded = true) apiKey: String,
        @Query(value = "units", encoded = true) units: String? = "metric"
    ): Response<WeatherResponseModel>?
}
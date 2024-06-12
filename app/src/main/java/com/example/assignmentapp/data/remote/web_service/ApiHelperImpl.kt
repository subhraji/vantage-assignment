package com.example.assignmentapp.data.remote.web_service

import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import com.example.assignmentapp.di.qualifier.ApiServiceQualifier
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    @ApiServiceQualifier
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getWeather(
        cityName: String,
        apiKey: String
    ): Response<WeatherResponseModel>? =
        apiService.getWeather(
            cityName = cityName,
            apiKey = apiKey
        )
}
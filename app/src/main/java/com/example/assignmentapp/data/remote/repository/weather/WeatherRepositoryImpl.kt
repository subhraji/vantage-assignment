package com.example.assignmentapp.data.remote.repository.weather

import android.util.Log
import com.example.assignmentapp.data.remote.model.NetworkResource
import com.example.assignmentapp.data.remote.model.ResponseModel
import com.example.assignmentapp.data.remote.model.ResponseStatus
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel
import com.example.assignmentapp.data.remote.web_service.ApiHelper
import com.example.assignmentapp.di.qualifier.ApiHelperQualifier
import com.example.assignmentapp.domain.repository.weather.WeatherRepository
import com.example.assignmentapp.utils.parseException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @ApiHelperQualifier
    private val apiHelper: ApiHelper,
    private val gson: Gson,
) : WeatherRepository {
    override suspend fun getWeatherData(
        cityName: String,
        apiKey: String
    ): NetworkResource<WeatherResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiHelper.getWeather(cityName = cityName, apiKey = apiKey)

                if (!response!!.isSuccessful) {
                    val errorResponse = parseError(response = response, gson = gson)

                    return@withContext NetworkResource(
                        data = response.body(),
                        message = errorResponse.message,
                        status = ResponseStatus.FAILED
                    )
                }

                NetworkResource.success(data = response?.body()!!)

            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResource.error(message = parseException(e))
            }
        }
    }
}
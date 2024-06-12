package com.example.assignmentapp.presentation.fragment.weather.weather_result.state

import com.example.assignmentapp.data.remote.model.NetworkResource
import com.example.assignmentapp.data.remote.model.weather.WeatherResponseModel

sealed class WeatherResultFragmentState {
    data class WeatherResultResponse(
        val response: NetworkResource<WeatherResponseModel>,
        val message: String? = null
    ) : WeatherResultFragmentState()
}
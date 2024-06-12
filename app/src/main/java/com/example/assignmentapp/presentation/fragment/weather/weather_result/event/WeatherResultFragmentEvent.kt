package com.example.assignmentapp.presentation.fragment.weather.weather_result.event

sealed class WeatherResultFragmentEvent {
    data class WeatherResult(
        val cityName: String,
        val apiKey: String
    ) : WeatherResultFragmentEvent()
}
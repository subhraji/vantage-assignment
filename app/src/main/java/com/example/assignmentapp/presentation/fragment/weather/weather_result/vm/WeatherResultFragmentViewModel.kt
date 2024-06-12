package com.example.assignmentapp.presentation.fragment.weather.weather_result.vm

import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.base.BaseViewModel
import com.example.assignmentapp.domain.usecase.weather.WeatherUseCase
import com.example.assignmentapp.presentation.fragment.weather.weather_result.event.WeatherResultFragmentEvent
import com.example.assignmentapp.presentation.fragment.weather.weather_result.state.WeatherResultFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherResultFragmentViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
): BaseViewModel<WeatherResultFragmentEvent, WeatherResultFragmentState>(){
    override fun addEvent(event: WeatherResultFragmentEvent) {
        when(event){
            is WeatherResultFragmentEvent.WeatherResult -> {
                getWeatherData(event)
            }
        }
    }

    private fun getWeatherData(event: WeatherResultFragmentEvent.WeatherResult){
        viewModelScope.launch(Dispatchers.IO){
            val response = weatherUseCase.getWeatherData(
                cityName = event.cityName,
                apiKey = event.apiKey
            )

            sendState(
                state = WeatherResultFragmentState.WeatherResultResponse(
                    response = response,
                    message = response.message
                )
            )
        }
    }
}
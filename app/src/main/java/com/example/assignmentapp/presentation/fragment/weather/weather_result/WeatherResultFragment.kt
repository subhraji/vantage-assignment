package com.example.assignmentapp.presentation.fragment.weather.weather_result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.assignmentapp.BuildConfig
import com.example.assignmentapp.R
import com.example.assignmentapp.data.remote.model.ResponseStatus
import com.example.assignmentapp.databinding.FragmentSearchWeatherBinding
import com.example.assignmentapp.databinding.FragmentWeatherResultBinding
import com.example.assignmentapp.presentation.fragment.weather.weather_result.event.WeatherResultFragmentEvent
import com.example.assignmentapp.presentation.fragment.weather.weather_result.state.WeatherResultFragmentState
import com.example.assignmentapp.presentation.fragment.weather.weather_result.vm.WeatherResultFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherResultFragment : Fragment() {

    var binding_: FragmentWeatherResultBinding? = null
    val binding get() = binding_!!

    private val viewModel by viewModels<WeatherResultFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentWeatherResultBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = navArgs<WeatherResultFragmentArgs>().value.cityName
        binding.weatherReportHtv.text = "Weather of ${cityName} :"

        startShimmer()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        handleState(state)
                    }
                }
                launch {
                    viewModel.addEvent(event = WeatherResultFragmentEvent.WeatherResult(
                        cityName = cityName,
                        apiKey = BuildConfig.API_KEY
                    ))
                }
            }
        }
    }

    private fun handleState(state: WeatherResultFragmentState){
        when(state){
            is WeatherResultFragmentState.WeatherResultResponse -> {
                handleWeatherDataResponse(state)
            }
        }
    }

    private fun handleWeatherDataResponse(state: WeatherResultFragmentState.WeatherResultResponse){
        if(state.response.isFailed()){
            val message = state.message ?: state.response.message ?: "Something went wrong"
            Snackbar.make(binding.root, "${message}}",
                Snackbar.LENGTH_SHORT).setAction("Action", null)
                .show()
            return
        }

        stopShimmer()
        val weather = state.response?.data?.weather?.first()?.main
        binding.weatherReportTv.text = "Weather : ${weather}"
    }

    private fun startShimmer(){
        binding.realLayout.isVisible = false
        binding.shimmerLayout.isVisible = true
        binding.shimmerLayout.startShimmer()
    }

    private fun stopShimmer(){
        binding.realLayout.isVisible = true
        binding.shimmerLayout.isVisible = false
        binding.shimmerLayout.stopShimmer()
    }
}
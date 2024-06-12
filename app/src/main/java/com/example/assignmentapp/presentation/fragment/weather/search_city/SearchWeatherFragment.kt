package com.example.assignmentapp.presentation.fragment.weather.search_city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.FragmentHomeBinding
import com.example.assignmentapp.databinding.FragmentSearchWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWeatherFragment : Fragment() {

    var binding_: FragmentSearchWeatherBinding? = null
    val binding get() = binding_!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentSearchWeatherBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            handleSearch()
        }
    }

    private fun handleSearch(){
        val cityName = binding.cityField.text
        if(cityName.isNullOrEmpty()){
            binding.cityFieldLayout.error = "City name is required."
            return
        }
        findNavController().navigate(SearchWeatherFragmentDirections.actionSearchWeatherFragmentToWeatherResultFragment(cityName = cityName.toString()))
    }
}
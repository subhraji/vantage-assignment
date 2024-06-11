package com.example.assignmentapp.presentation.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    var binding_: FragmentHomeBinding? = null
    val binding get() = binding_!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.taskManagerBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTaskListFragment())
        }
    }
}
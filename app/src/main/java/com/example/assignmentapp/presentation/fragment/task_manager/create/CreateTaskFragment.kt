package com.example.assignmentapp.presentation.fragment.task_manager.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.data.remote.model.task.TaskModel
import com.example.assignmentapp.databinding.FragmentCreateTaskBinding
import com.example.assignmentapp.presentation.fragment.task_manager.create.event.CreateTaskFragmentEvent
import com.example.assignmentapp.presentation.fragment.task_manager.create.state.CreateTaskFragmentState
import com.example.assignmentapp.presentation.fragment.task_manager.create.vm.CreateTaskFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    var binding_: FragmentCreateTaskBinding? = null
    val binding get() = binding_!!

    private val viewModel by viewModels<CreateTaskFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentCreateTaskBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        handleState(state)
                    }
                }
            }
        }

        binding.createBtn.setOnClickListener {
            handleSubmit()
        }
    }

    fun handleSubmit(){
        val event = getSubmitEvent()

        if (!validateEvent(event)) {
            return
        }

        viewModel.addEvent(event = event)
    }

    private fun getSubmitEvent(): CreateTaskFragmentEvent.SaveTask {
        var title = binding.titleField.text.toString()
        var description = binding.descriptionField.text.toString()
        var taskModel = TaskModel(
            title = title,
            description = description,
        )
        return CreateTaskFragmentEvent.SaveTask(
            taskModel = taskModel
        )
    }


    private fun validateEvent(event: CreateTaskFragmentEvent.SaveTask): Boolean {
        var valid = true
        val errors = JsonObject()

        if (event.taskModel.title!!.isEmpty()) {
            errors.add("title", JsonArray().apply {
                add("Title is requires.")
            })
            valid = false
        }
        if (event.taskModel.description!!.isEmpty()) {
            errors.add("description", JsonArray().apply {
                add("Description is required.")
            })
            valid = false
        }

        renderInlineErrors(errors)

        return valid
    }

    private fun renderInlineErrors(errors: JsonObject) {

        errors.keySet().forEach { key ->
            val errorMessage = errors[key].asJsonArray[0].asString
            when (key) {
                "title" -> {
                    binding.titleFieldLayout.error = errorMessage
                }

                "description" -> {
                    binding.descriptionFieldLayout.error = errorMessage
                }
            }
        }

    }
    private fun handleState(state: CreateTaskFragmentState) {
        when (state) {
            is CreateTaskFragmentState.SaveTaskResponse -> {
                handleCreateTaskResponse(state)
            }
        }
    }

    private fun handleCreateTaskResponse(state: CreateTaskFragmentState.SaveTaskResponse){
        if(state.response != null){
            Snackbar.make(binding.root, "Your task has been saved.",
                Snackbar.LENGTH_SHORT).setAction("Action", null)
                .show()
            findNavController().popBackStack()
        }else{
            Snackbar.make(binding.root, "Something went wrong!",
                Snackbar.LENGTH_SHORT).setAction("Action", null)
                .show()
        }
    }
}
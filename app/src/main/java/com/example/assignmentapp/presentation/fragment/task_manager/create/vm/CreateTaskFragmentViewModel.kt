package com.example.assignmentapp.presentation.fragment.task_manager.create.vm

import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.base.BaseViewModel
import com.example.assignmentapp.data.remote.model.task.TaskModel
import com.example.assignmentapp.domain.usecase.task_manager.TaskManagerUseCae
import com.example.assignmentapp.presentation.fragment.task_manager.create.event.CreateTaskFragmentEvent
import com.example.assignmentapp.presentation.fragment.task_manager.create.state.CreateTaskFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskFragmentViewModel @Inject constructor(
    private val taskManagerUseCae: TaskManagerUseCae
) : BaseViewModel<CreateTaskFragmentEvent, CreateTaskFragmentState>() {
    override fun addEvent(event: CreateTaskFragmentEvent) {
        when(event){
            is CreateTaskFragmentEvent.SaveTask -> {
                saveTask(event.taskModel)
            }
        }
    }

    private fun saveTask(taskModel: TaskModel){
        viewModelScope.launch(Dispatchers.IO){
            val response = taskManagerUseCae.saveTask(taskModel = taskModel)
            sendState(
                state = CreateTaskFragmentState.SaveTaskResponse(
                    response = response
                )
            )
        }
    }
}
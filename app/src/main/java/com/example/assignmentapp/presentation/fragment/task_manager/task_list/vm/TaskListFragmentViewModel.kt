package com.example.assignmentapp.presentation.fragment.task_manager.task_list.vm

import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.base.BaseViewModel
import com.example.assignmentapp.domain.usecase.task_manager.TaskManagerUseCae
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.event.TaskListFragmentEvent
import com.example.assignmentapp.presentation.fragment.task_manager.task_list.state.TaskListFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListFragmentViewModel @Inject constructor(
    private val taskManagerUseCae: TaskManagerUseCae
) : BaseViewModel<TaskListFragmentEvent, TaskListFragmentState>(){
    override fun addEvent(event: TaskListFragmentEvent) {
        when(event){
            is TaskListFragmentEvent.GetTasks -> {
                getTasks()
            }

            is TaskListFragmentEvent.DeleteTask -> {
                deleteTask(event)
            }
        }
    }

    private fun getTasks(){
        viewModelScope.launch(Dispatchers.IO){
            val response = taskManagerUseCae.getTasks()

            sendState(
                state = TaskListFragmentState.GetTasksResponse(
                    response = response
                )
            )
        }
    }

    private fun deleteTask(event: TaskListFragmentEvent.DeleteTask){
        viewModelScope.launch(Dispatchers.IO){
            taskManagerUseCae.deleteTask(taskModel = event.taskModel)
            sendState(
                state = TaskListFragmentState.DeleteTaskResponse
            )
        }
    }
}
package com.example.assignmentapp.presentation.fragment.task_manager.task_list.state

import com.example.assignmentapp.data.remote.model.TaskModel

sealed class TaskListFragmentState {
    data class GetTasksResponse(
        val response: List<TaskModel>
    ) : TaskListFragmentState()
}
package com.example.assignmentapp.presentation.fragment.task_manager.task_list.event

import com.example.assignmentapp.data.remote.model.task.TaskModel

sealed class TaskListFragmentEvent {
    object GetTasks : TaskListFragmentEvent()

    data class DeleteTask(val taskModel: TaskModel) : TaskListFragmentEvent()
}
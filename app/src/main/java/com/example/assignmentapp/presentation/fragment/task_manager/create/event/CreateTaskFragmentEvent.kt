package com.example.assignmentapp.presentation.fragment.task_manager.create.event

import com.example.assignmentapp.data.remote.model.TaskModel

sealed class CreateTaskFragmentEvent {
    data class SaveTask(
        val taskModel: TaskModel
    ) : CreateTaskFragmentEvent()
}
package com.example.assignmentapp.domain.usecase.task_manager

import com.example.assignmentapp.data.remote.model.TaskModel
import com.example.assignmentapp.domain.repository.task_manager.TaskManagerRepository
import javax.inject.Inject

class TaskManagerUseCae @Inject constructor(
    private val taskManagerRepository: TaskManagerRepository
) {

    suspend fun saveTask(taskModel: TaskModel) : Long {
        return taskManagerRepository.saveTask(taskModel = taskModel)
    }

}
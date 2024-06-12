package com.example.assignmentapp.domain.usecase.task_manager

import com.example.assignmentapp.data.remote.model.task.TaskModel
import com.example.assignmentapp.domain.repository.task_manager.TaskManagerRepository
import javax.inject.Inject

class TaskManagerUseCae @Inject constructor(
    private val taskManagerRepository: TaskManagerRepository
) {

    suspend fun saveTask(taskModel: TaskModel) : Long {
        return taskManagerRepository.saveTask(taskModel = taskModel)
    }

    suspend fun getTasks() : List<TaskModel> {
        return taskManagerRepository.getTasks()
    }

    suspend fun deleteTask(taskModel: TaskModel) {
        taskManagerRepository.deleteTask(taskModel = taskModel)
    }
}
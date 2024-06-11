package com.example.assignmentapp.domain.repository.task_manager

import com.example.assignmentapp.data.remote.model.TaskModel
import com.example.assignmentapp.domain.repository.BaseRepository

interface TaskManagerRepository : BaseRepository {

    suspend fun saveTask(taskModel: TaskModel) : Long

}
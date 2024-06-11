package com.example.assignmentapp.data.remote.repository.task_manager

import com.example.assignmentapp.data.remote.model.TaskModel
import com.example.assignmentapp.db.dao.TaskDao
import com.example.assignmentapp.domain.repository.task_manager.TaskManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskManagerRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskManagerRepository {
    override suspend fun saveTask(taskModel: TaskModel) : Long {
        return withContext(Dispatchers.IO) {
            try {
                val response = taskDao.save(taskEntity = taskModel.convertToTaskEntity())
                return@withContext response
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext -1
            }
        }
    }
}
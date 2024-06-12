package com.example.assignmentapp.data.remote.repository.task_manager

import android.util.Log
import com.example.assignmentapp.data.remote.model.task.TaskModel
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

    override suspend fun getTasks(): List<TaskModel> {
        return withContext(Dispatchers.IO) {
            try {
                taskDao.getAll()?.let{ tasks ->
                    tasks.map { it.convertToTaskModel() }
                } ?: emptyList()
            } catch (e: Exception){
                e.printStackTrace()
                emptyList<TaskModel>()
            }

        }
    }

    override suspend fun deleteTask(taskModel: TaskModel) {
        withContext(Dispatchers.IO) {
            try {
                taskDao.deleteTaskById(taskModel.id!!.toLong())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
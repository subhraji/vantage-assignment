package com.example.assignmentapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentapp.db.entity.TaskModelEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(taskEntity: TaskModelEntity): Long

    @Query("SELECT * FROM task")
    fun getAll(): List<TaskModelEntity>?

    @Delete
    suspend fun deleteTask(taskEntity: TaskModelEntity)

    @Query("DELETE FROM task")
    suspend fun deleteAllTask()

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Long)
}
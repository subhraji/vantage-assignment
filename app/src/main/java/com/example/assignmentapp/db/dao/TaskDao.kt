package com.example.assignmentapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.assignmentapp.db.entity.TaskModelEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(taskEntity: TaskModelEntity): Long
}
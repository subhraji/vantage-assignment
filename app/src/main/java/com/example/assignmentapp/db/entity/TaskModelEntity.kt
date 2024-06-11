package com.example.assignmentapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignmentapp.data.remote.model.TaskModel

@Entity(tableName = "task")
data class TaskModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title: String? = null,
    val description: String? = null,
    val createdAt: Long? = null
){
    fun convertToTaskModel(): TaskModel {
        return TaskModel(
            id = id,
            title = title,
            description = description,
            createdAt = createdAt
        )
    }
}

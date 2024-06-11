package com.example.assignmentapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title: String? = null,
    val description: String? = null,
    val createdAt: Long? = null
)

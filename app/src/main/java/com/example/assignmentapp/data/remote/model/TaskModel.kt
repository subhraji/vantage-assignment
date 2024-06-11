package com.example.assignmentapp.data.remote.model

import android.os.Parcelable
import com.example.assignmentapp.db.entity.TaskModelEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel (
    val id : Int? = null,
    val title: String? = null,
    val description: String? = null,
    val createdAt: Long? = null
): Parcelable {
    fun convertToTaskEntity(): TaskModelEntity {
        return TaskModelEntity(
            title = title,
            description = description,
            createdAt = System.currentTimeMillis()
        )
    }
}
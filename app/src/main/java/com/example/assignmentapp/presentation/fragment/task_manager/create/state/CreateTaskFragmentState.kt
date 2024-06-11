package com.example.assignmentapp.presentation.fragment.task_manager.create.state

sealed class CreateTaskFragmentState {
    data class SaveTaskResponse(
        val response: Long
    ): CreateTaskFragmentState()
}
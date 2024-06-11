package com.example.assignmentapp.presentation.fragment.task_manager.task_list.event

sealed class TaskListFragmentEvent {
    object GetTasks : TaskListFragmentEvent()
}
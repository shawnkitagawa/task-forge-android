package com.example.taskforge.viewmodel

data class EditTaskUiState(
    val taskId: Int? = null,
    val taskName: String = "",
    val taskDescription: String = "",
    val deadline: Long? = null,
    val completed: Boolean = false,
)

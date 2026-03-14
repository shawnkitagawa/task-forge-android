package com.example.taskforge.viewmodel

data class EditTaskUiState(
    val taskId: Int = 0 ,
    val taskName: String = "",
    val taskDescription: String = "",
    val deadLine: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val completed: Boolean = false,
)

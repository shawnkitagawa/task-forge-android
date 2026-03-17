package com.example.taskforge.viewmodel

data class AddTaskUiState(
    val taskId: Int = 0 ,
    val taskName: String = "",
    val taskDescription: String = "",
    val deadlineText:String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val completed: Boolean = false,
    val dateError: String? = null ,
    val nameError: String? = null,
    val descriptionError: String? = null,

)

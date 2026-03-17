package com.example.taskforge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0 ,
    val taskName: String,
    val taskDescription: String,
    val deadLine: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val completed: Boolean = false,
)

package com.example.taskforge.application

import android.content.Context
import com.example.taskforge.data.TaskDatabase
import com.example.taskforge.repository.DefaultTaskRepository
import com.example.taskforge.repository.TaskRepository

interface AppContainer {

    val taskRepository: TaskRepository
}

class AppDataContainer(private val context: Context): AppContainer
{
    override val taskRepository: TaskRepository by lazy{
        // taskRepositiory is the instance name of DefaultTaskRepositiory
        DefaultTaskRepository(TaskDatabase.getDatabase(context).taskDao())
    }
}
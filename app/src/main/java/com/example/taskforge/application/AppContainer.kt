package com.example.taskforge.application

import com.example.taskforge.repository.TaskRepository

interface AppContainer {

    val taskRepository: TaskRepository
}






class AppDataContainer()
{


}
package com.example.taskforge.repository

import com.example.taskforge.data.TaskDao
import com.example.taskforge.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task:Task)

    suspend fun updateTask(task:Task)

    fun tasks(): Flow<List<Task>>

    fun showActiveTask(): Flow<List<Task>>

    fun showCompletedTask(): Flow<List<Task>>

    // require for detail Screen or edit screen
    fun getTask(id: Int): Flow<Task?>

    suspend fun  markComplete(id: Int)

    suspend fun unmarkComplete(id: Int)
}


class DefaultTaskRepository(private val taskDao: TaskDao): TaskRepository
{
    override suspend fun addTask(task: Task) = taskDao.insert(task)

    override suspend fun deleteTask(task:Task) = taskDao.delete(task)

    override suspend fun updateTask(task:Task) = taskDao.updateTask(task)

    override fun tasks(): Flow<List<Task>> = taskDao.tasks()

    override fun showActiveTask(): Flow<List<Task>> = taskDao.activeTask()

    override fun showCompletedTask(): Flow<List<Task>> = taskDao.completedTask()

    // require for detail Screen or edit screen
    override fun getTask(id: Int): Flow<Task?> = taskDao.getTask(id)

    override suspend fun  markComplete(id: Int) = taskDao.markCompleted(id)

    override suspend fun unmarkComplete(id: Int) = taskDao.unmarkCompleted(id)
}
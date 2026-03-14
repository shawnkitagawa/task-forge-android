package com.example.taskforge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskforge.application.TaskForgeApplication
import com.example.taskforge.model.Task
import com.example.taskforge.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.Int

class EditTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(EditTaskUiState())

    val uiState: StateFlow<EditTaskUiState> = _uiState

    fun addTask()
    {
        val task = currentTask()
        viewModelScope.launch{
            taskRepository.addTask(task)
        }

    }
    fun deleteTask()
    {
        val task = currentTask()
        viewModelScope.launch{
            taskRepository.deleteTask(task)
        }

    }

    fun updateTask()
    {
        val task = currentTask()
        viewModelScope.launch{
            taskRepository.updateTask(task)
        }
    }

    // For the textfield to change while typing
    fun updateTaskName(name: String)
    {
        _uiState.value = _uiState.value.copy(taskName = name)

    }
    fun updateTaskDescription(description: String)
    {
        _uiState.value = _uiState.value.copy(taskName = description)
    }
    fun updateDeadLine(deadLine: Long)
    {
        _uiState.value = _uiState.value.copy(deadLine = deadLine)
    }

    fun loadTask(id: Int)
    {
        viewModelScope.launch {
            val task = taskRepository.getTask(id).first()
            if (task != null)
            {
                _uiState.value = _uiState.value.copy(
                    taskId =  task.taskId ,
                    taskName = task.taskName,
                    taskDescription = task.taskDescription,
                    deadLine = task.deadLine,
                    completed = task.completed

                )
            }
        }
    }

    //helper function
    private fun currentTask(): Task{
        val state = _uiState.value
        val task = Task(
            taskId =  state.taskId ,
            taskName = state.taskName,
            taskDescription = state.taskDescription,
            deadLine = state.deadLine,
            completed = state.completed
        )
        return task
    }

    companion object
    {
        val Factory: ViewModelProvider.Factory = viewModelFactory{

            initializer {
                val application = (this[APPLICATION_KEY] as TaskForgeApplication)
                val taskRepository = application.container.taskRepository

                EditTaskViewModel(taskRepository = taskRepository)
            }
        }
    }

}

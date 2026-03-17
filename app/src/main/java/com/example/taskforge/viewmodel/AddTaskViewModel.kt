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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.Int
import android.util.Log
import com.example.taskforge.helper.dateConverter


class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(AddTaskUiState())

    val uiState: StateFlow<AddTaskUiState> = _uiState

    fun addTask()
    {
        val task = currentTask() ?: return
        viewModelScope.launch{
            taskRepository.addTask(task)
            _uiState.value = _uiState.value.copy(
                deadlineText = "",
                taskName = "",
                taskDescription = "",
                dateError = null,
                nameError = null,
                descriptionError = null,
            )
        }

    }
    fun deleteTask(onSucess: () -> Unit )
    {
        val task = currentTask() ?:  return
        viewModelScope.launch{
            taskRepository.deleteTask(task)
            onSucess()
        }

    }
    fun updateTask(onSuccess: () -> Unit)
    {
        val task = currentTask() ?: return
        viewModelScope.launch{
            taskRepository.updateTask(task)
            _uiState.value = _uiState.value.copy(
                dateError = null,
                nameError = null,
                descriptionError = null,
            )
            onSuccess()
        }
    }

    // For the textfield to change while typing
    fun updateTaskName(name: String)
    {
        _uiState.value = _uiState.value.copy(taskName = name, nameError = null)

    }
    fun updateTaskDescription(description: String)
    {
        _uiState.value = _uiState.value.copy(taskDescription = description, descriptionError = null)
    }
    fun updateDeadLine( deadlineText: String)
    {
        _uiState.update{
            it.copy(
                deadlineText = deadlineText,
                dateError = null
            )
        }
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
                    deadlineText = dateConverter.longTostring(task.deadLine),
                    completed = task.completed

                )
            }
        }
    }
    //helper function
    private fun currentTask(): Task?{
        val state = _uiState.value
        val deadline = dateConverter.stringTolong(state.deadlineText)

        val dateError = if(deadline == null) "Please enter a valid date in yyyy-MM-dd format" else {null}

        val nameError = if(state.taskName == "")"Please fill in the Title" else null

        val descriptionError = if(state.taskDescription == "")"Please fill in the Description" else null

        if (dateError != null || nameError != null || descriptionError != null)
        {
            _uiState.value = _uiState.value.copy(
                dateError = dateError,
                nameError = nameError,
                descriptionError = descriptionError
            )
            return null
        }

        return  Task(
            taskId =  state.taskId ,
            taskName = state.taskName,
            taskDescription = state.taskDescription,
            deadLine = deadline!! ,
            completed = state.completed,
        )
    }
    companion object
    {
        val Factory: ViewModelProvider.Factory = viewModelFactory{

            initializer {
                val application = (this[APPLICATION_KEY] as TaskForgeApplication)
                val taskRepository = application.container.taskRepository

                AddTaskViewModel(taskRepository = taskRepository)
            }
        }
    }

}

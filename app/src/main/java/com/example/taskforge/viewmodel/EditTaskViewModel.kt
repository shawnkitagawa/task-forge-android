package com.example.taskforge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskforge.application.TaskForgeApplication
import com.example.taskforge.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTaskUiState())

    val uiState: StateFlow<EditTaskUiState> = _uiState

    companion object
    {
        val Factory: ViewModelProvider.Factory = viewModelFactory{

            initializer {
                val application = (this[APPLICATION_KEY] as TaskForgeApplication)
                val taskRepository = application.container.taskRepository

                TaskViewModel(taskRepository = taskRepository)
            }
        }
    }

}
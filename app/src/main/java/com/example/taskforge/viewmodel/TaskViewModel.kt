package com.example.taskforge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskforge.application.TaskForgeApplication
import com.example.taskforge.model.Task
import com.example.taskforge.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {

    private val _selectedFilter = MutableStateFlow(TaskFilter.ALL)
    val selectedFilter : StateFlow<TaskFilter> = _selectedFilter

    val taskFlow: StateFlow<List<Task>> = _selectedFilter.flatMapLatest { filter ->
        println("flatMapLatest -> $filter")
        when(filter)
        {
            TaskFilter.ALL -> taskRepository.tasks()
            TaskFilter.ACTIVE -> taskRepository.showActiveTask()
            TaskFilter.COMPLETED -> taskRepository.showCompletedTask()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()

    )
//    fun markComplete(id: Int)
//    {
//        viewModelScope.launch {
//            taskRepository.markComplete(id)
//        }
//    }
fun completeMarker(task: Task) {
    viewModelScope.launch {
        taskRepository.updateTask(task.copy(completed = !task.completed))
    }
}
    fun updateFiler(filter: TaskFilter) {
        if (_selectedFilter.value == filter) return

        println("setFilter: $filter at ${System.currentTimeMillis()}")
        _selectedFilter.value = filter
        println("Filter changed: $filter")
    }



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
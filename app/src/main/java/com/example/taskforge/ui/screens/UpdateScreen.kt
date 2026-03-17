package com.example.taskforge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Update
import com.example.taskforge.model.Task
import com.example.taskforge.viewmodel.AddTaskUiState
import com.example.taskforge.viewmodel.AddTaskViewModel

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = viewModel(factory = AddTaskViewModel.Factory),
    navigateToHome: () -> Unit,
    taskid: Int) {


    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskid)
    {
        viewModel.loadTask(taskid)
    }
    UpdateScreenContext(
        task = uiState,
        onTaskNameUpdate = {viewModel.updateTaskName(it)},
        onTaskDescriptionUpdate = {viewModel.updateTaskDescription(it)},
        onTaskDeadLineUpdate = {viewModel.updateDeadLine(it)},
        updateTask = {viewModel.updateTask(onSuccess = {navigateToHome()})},
        navigateToHome = navigateToHome,
        onDelete = {viewModel.deleteTask(onSucess = {navigateToHome()})})

}


@Composable
fun UpdateScreenContext(
    task: AddTaskUiState,
    onTaskNameUpdate: (String) -> Unit,
    onTaskDescriptionUpdate: (String) -> Unit,
    onTaskDeadLineUpdate: (String) -> Unit,
    updateTask: () -> Unit,
    navigateToHome: () -> Unit,
    onDelete: () -> Unit,

)
{

    Column(modifier = Modifier.padding(16.dp) ,horizontalAlignment = Alignment.CenterHorizontally)
    {
        UpdateTaskTextField(value = task.taskName, label = "Task Name", onValueChange = onTaskNameUpdate, isError = task.nameError != null, errorMessage = task.nameError )
        UpdateTaskTextField(value = task.taskDescription, label = "Task Description", onValueChange = onTaskDescriptionUpdate, isError = task.descriptionError!= null, errorMessage = task.descriptionError)
        UpdateTaskTextField(value = task.deadlineText.toString(), label = "yyyy-MM-dd", onValueChange = onTaskDeadLineUpdate, isError = task.dateError!= null, errorMessage = task.dateError)
        Button(
            onClick = {updateTask()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("UpdateTask")
        }
        Button(
            onClick = {navigateToHome()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Go Back ")
        }
        Button(
            onClick = {onDelete()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Delete this Task ")
        }

    }
}

@Composable
fun UpdateTaskTextField(
    modifier: Modifier = Modifier,
    value : String,
    label : String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
)
{
    TextField(
        value = value,
        onValueChange = { newText -> onValueChange(newText)},
        label = {Text(label)},
        isError = isError,
        supportingText = {
            errorMessage?.let{
                Text(it)
            }
        }
    )
}
package com.example.taskforge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskforge.ui.theme.TaskForgeTheme
import com.example.taskforge.viewmodel.AddTaskUiState
import com.example.taskforge.viewmodel.AddTaskViewModel

@Composable
fun AddScreen(
               modifier: Modifier = Modifier,
               viewModel: AddTaskViewModel = viewModel(factory = AddTaskViewModel.Factory),
               navigateToHome: () -> Unit)
{
    val uiState by viewModel.uiState.collectAsState()
    AddScreenContent(uiState = uiState,
        onTaskNameChange = {viewModel.updateTaskName(it)},
        onTaskDescriptionChange = {viewModel.updateTaskDescription(it)},
        onTaskDeadLineChange = {viewModel.updateDeadLine(it)},
        addTask = { viewModel.addTask() },
        navigateToHome = navigateToHome)
}

@Composable
fun AddScreenContent(
    uiState: AddTaskUiState,
    onTaskNameChange: (String) -> Unit,
    onTaskDescriptionChange: (String) -> Unit,
    onTaskDeadLineChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    addTask: () -> Unit,
    navigateToHome: () -> Unit,
)
{
    Column(modifier = Modifier.padding(16.dp) ,horizontalAlignment = Alignment.CenterHorizontally)
    {
        TaskTextField(value = uiState.taskName, label = "Task Name", onValueChange = onTaskNameChange, isError = uiState.nameError != null, errorMessage = uiState.nameError)
        TaskTextField(value = uiState.taskDescription, label = "Task Description", onValueChange = onTaskDescriptionChange, isError = uiState.descriptionError!= null, errorMessage = uiState.descriptionError)
        TaskTextField(value = uiState.deadlineText, label = "yyyy-MM-dd", onValueChange = onTaskDeadLineChange, isError = uiState.dateError!= null, errorMessage = uiState.dateError)
        Button(
            onClick = {addTask()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Add Task ")
        }
        Button(
            onClick = {navigateToHome()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Go Back ")
        }

    }
}
@Composable
fun TaskTextField(
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
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    TaskForgeTheme {

    }
}
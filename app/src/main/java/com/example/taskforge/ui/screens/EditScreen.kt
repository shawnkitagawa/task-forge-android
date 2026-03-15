package com.example.taskforge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toString
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskforge.ui.theme.TaskForgeTheme
import com.example.taskforge.viewmodel.EditTaskUiState
import com.example.taskforge.viewmodel.EditTaskViewModel

@Composable
fun EditScreen(
               modifier: Modifier = Modifier,
               viewModel: EditTaskViewModel = viewModel(factory = EditTaskViewModel.Factory)
)
{
    val uiState by viewModel.uiState.collectAsState()
    EditScreenContent(uiState = uiState,
        onTaskNameChange = {viewModel.updateTaskName(it)},
        onTaskDescriptionChange = {viewModel.updateTaskDescription(it)},
        onTaskDeadLineChange = {viewModel.updateDeadLine(it)},
//        onTaskCompleteChange = {viewModel.},
        addTask = { viewModel.addTask() })
}

@Composable
fun EditScreenContent(
    uiState: EditTaskUiState,
    onTaskNameChange: (String) -> Unit,
    onTaskDescriptionChange: (String) -> Unit,
    onTaskDeadLineChange: (String) -> Unit,
//    onTaskCompleteChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    addTask: () -> Unit,
)
{
    Column()
    {
        TaskTextField(value = uiState.taskName, label = "Task Name", onValueChange = onTaskNameChange)
        TaskTextField(value = uiState.taskDescription, label = "Task Description", onValueChange = onTaskDescriptionChange)
        TaskTextField(value = uiState.deadlineText, label = "yyyy-MM-dd", onValueChange = onTaskDeadLineChange, isError = uiState.dataError!= null, errorMessage = uiState.dataError)
//        Checkbox(
//            checked = uiState.completed,
//            onCheckedChange = { isChecked -> onTaskCompleteChange(isChecked)}
//        )
        Button(
            onClick = {addTask()},
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Add Task ")
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
fun EditScreenPreview() {
    TaskForgeTheme {
//        EditScreenContent(name = "Task", onTaskNameChange = {},
//            )
    }
}
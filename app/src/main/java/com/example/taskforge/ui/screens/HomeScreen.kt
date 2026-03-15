package com.example.taskforge.ui.screens

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskforge.viewmodel.TaskFilter
import com.example.taskforge.viewmodel.TaskViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory))
{
    val tasks by viewModel.taskFlow.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally )
    {


        filtering(
            filter = selectedFilter,
            filterAll = { viewModel.updateFiler(TaskFilter.ALL) },
            filterActive = { viewModel.updateFiler(TaskFilter.ACTIVE) },
            filterCompleted = { viewModel.updateFiler(TaskFilter.COMPLETED) }
        )

        LazyColumn(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
        {
            items(tasks, key = {task -> task.taskId}) { task ->
                var clicked by remember(task.taskId) { mutableStateOf(false) }
                Card()
                {
                    Row()
                    {
                        Text(text = task.taskName)
                        Text(text = task.deadLine.toString())
                        IconButton(
                            onClick = { viewModel.completeMarker(task.taskId) }
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Completed",
                                tint = if (task.completed)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.Gray
                            )
                        }

                        IconButton(
                            onClick = {clicked = !clicked }
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = if(clicked == true) {
                                    MaterialTheme.colorScheme.primary
                                }
                                else
                                {
                                    Color.Gray
                                }


                            )
                        }
                    }
                    Text(text = task.taskDescription)
                }
            }

        }
    }
}
@Composable
fun filtering(modifier: Modifier = Modifier,
              filter: TaskFilter,
              filterAll: () -> Unit,
              filterActive: () -> Unit,
              filterCompleted: () -> Unit)
{
    Row()
    {
        FilterButton(
            text = "ALL",
            selected = filter == TaskFilter.ALL,
            onClick = filterAll
        )
        FilterButton(
            text = "Active",
            selected = filter == TaskFilter.ACTIVE,
            onClick = filterActive
        )
        FilterButton(
            text = "COMPLETED",
            selected = filter == TaskFilter.COMPLETED,
            onClick = filterCompleted
        )
    }
}
@Composable
fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick

    )
    {
        Text(text = text)
    }
}

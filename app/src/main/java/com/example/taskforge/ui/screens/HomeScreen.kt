package com.example.taskforge.ui.screens

import android.graphics.Paint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskforge.Greeting
import com.example.taskforge.R
import com.example.taskforge.helper.dateConverter.longTostring
import com.example.taskforge.helper.dateConverter.stringTolong
import com.example.taskforge.model.Task
import com.example.taskforge.viewmodel.TaskFilter
import com.example.taskforge.viewmodel.TaskViewModel
import kotlinx.coroutines.selects.select
import java.util.logging.Filter
import kotlin.Int
import kotlin.String
import kotlin.math.exp







@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory),
               navigateToEdit: (Int) -> Unit, ) {
    val tasks by viewModel.taskFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row()
        {
            Buttons(updateFilter = {viewModel.updateFiler(it)},
                all = TaskFilter.ALL,
                active = TaskFilter.ACTIVE,
                completed = TaskFilter.COMPLETED)
        }
        TodoList(tasks = tasks, navigateToEdit = {navigateToEdit(it)}, checkComplete = {viewModel.completeMarker(it)}, onClose = {})
    }
}
@Composable
fun Buttons(modifier: Modifier = Modifier,
            updateFilter: (TaskFilter) -> Unit,
            all: TaskFilter,
            active: TaskFilter,
            completed: TaskFilter
            )
{
    FilterButton(updateFilter = {updateFilter(all)}, buttonName = stringResource(
        R.string.all
    ), filter = TaskFilter.ALL)

    FilterButton(updateFilter = {updateFilter(active)}, buttonName = stringResource(
        R.string.active
    ), filter = TaskFilter.ACTIVE)

    FilterButton(updateFilter = {updateFilter(completed)}, buttonName = stringResource(
        R.string.completed
    ), filter = TaskFilter.COMPLETED)

}


@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    filter: TaskFilter,
    updateFilter: (TaskFilter) -> Unit,
    buttonName: String,
)
{
    Button(
        onClick = {updateFilter(filter)}

    ) {
        Text(buttonName)
    }
}


@Composable
fun TodoList(modifier: Modifier = Modifier,
             tasks: List<Task>,
             navigateToEdit: (Int) -> Unit,
             checkComplete: (Task) -> Unit,
             onClose: (Task) -> Unit,
             )
{
    LazyColumn(modifier = Modifier)
    {
        items(tasks, key = { it.taskId }) { task ->
            TodoItem(task = task , navigateToEdit = {navigateToEdit(it)}, checkComplete = {checkComplete(task)}, onClose = {onClose(task)})
        }
    }
}

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    task: Task,
    navigateToEdit: (Int) -> Unit,
    checkComplete: (Task) -> Unit,
    onClose: (Task) -> Unit
) {
    var expand by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.taskName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = longTostring(task.deadLine),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                TodoIcon(
                    expand = expand,
                    toggle = { expand = !expand }
                )

                TodoEditIcon(
                    navigateToEdit = { navigateToEdit(task.taskId) },
                    taskId = task.taskId
                )

                TodoComplete(
                    task = task,
                    checkComplete = { checkComplete(task) }
                )
            }

            if (expand) {
                Spacer(modifier = Modifier.height(8.dp))
                TodoDescription(description = task.taskDescription)
            }
        }
    }
}


@Composable
fun TodoComplete(modifier: Modifier = Modifier, checkComplete: (Task) -> Unit, task: Task)
{
    IconButton(
        onClick = {checkComplete(task)}
    )
    {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Completed",
            tint = if(task.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,

        )
    }
}



@Composable
fun TodoEditIcon(modifier: Modifier = Modifier,
                 navigateToEdit: (Int) -> Unit,
                 taskId : Int)
{
    IconButton(
        onClick = { navigateToEdit(taskId)}
    )
    {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Editing",

        )
    }
}
@Composable
fun TodoIcon(modifier: Modifier = Modifier,
             toggle: (Boolean) -> Unit,
             expand: Boolean)
{
    IconButton(
        onClick = {toggle(expand)},
    )
    {
        Icon(
            imageVector = if(expand) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess ,
            contentDescription = "Expand"
        )
    }
}
@Composable
fun TodoDescription(modifier: Modifier = Modifier,
                    description: String)
{
    Text(text = description, modifier = Modifier.padding(start = 15.dp, bottom = 8.dp))
}




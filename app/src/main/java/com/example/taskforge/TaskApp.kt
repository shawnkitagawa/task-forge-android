package com.example.taskforge

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskforge.ui.navigation.TaskNavHost
import com.example.taskforge.ui.screens.TaskTopBar

@Composable
fun TaskApp(
    navController: NavHostController = rememberNavController()) {

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route


    Scaffold(
        floatingActionButton = {
            if (currentRoute == "home") {
                FloatingActionButton(
                    onClick = { navController.navigate(route = "add") }
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TaskTopBar()
        }

    )
    { it ->
        TaskNavHost(modifier = Modifier.padding(it), navController = navController)
    }


}
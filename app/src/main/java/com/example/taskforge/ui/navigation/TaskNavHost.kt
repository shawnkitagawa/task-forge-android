package com.example.taskforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskforge.ui.screens.AddScreen

import com.example.taskforge.ui.screens.HomeScreen
import com.example.taskforge.ui.screens.UpdateScreen


@Composable
fun TaskNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController, startDestination = "home" ,modifier = modifier
    )
    {
        composable (route = "home")
        {
            HomeScreen(navigateToAdd = {navController.navigate("add")},
                navigateToEdit = {taskId -> navController.navigate("update/$taskId")})

        }
        composable (route = "add")
        {
            AddScreen(navigateToHome = {navController.navigate("home")})

        }
        composable (route = "update/{taskId}")
        { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toInt() ?:0

            UpdateScreen(taskid = taskId, navigateToHome = {navController.navigate("home")})
        }
    }

}
package com.example.taskforge

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskforge.ui.navigation.TaskNavHost

@Composable
fun TaskApp(
    navController: NavHostController = rememberNavController())
{
    TaskNavHost(navController = navController)
}


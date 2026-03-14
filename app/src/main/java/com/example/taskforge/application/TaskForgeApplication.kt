package com.example.taskforge.application


import android.app.Application
class TaskForgeApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate()
    {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
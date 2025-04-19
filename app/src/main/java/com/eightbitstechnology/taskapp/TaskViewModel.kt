package com.eightbitstechnology.taskapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val db = TaskDatabase.getDatabase(application)
    private val taskDao = db.taskDao()


    //Get all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.createNewTasK(task)
        }
    }


    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }


}
package com.eightbitstechnology.taskapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {

    // create new task
    @Insert
    suspend fun createNewTasK(newTask: Task)


    //update task
    @Update
    suspend fun updateTask(task: Task)


    //delete task
    @Delete
    suspend fun deleteTask(task: Task)


    //get all tasks
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

}
package com.example.taskforge.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskforge.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task:Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("""
        SELECT * FROM task
        ORDER BY deadLine ASC
    """)
    fun orderDeadLine(): Flow<List<Task>>

    @Query("""
        SELECT * FROM task 
        Where completed = 0 
        ORDER By taskId ASC
    """)
    fun activeTask(): Flow<List<Task>>

    @Query("""
        SELECT * FROM task 
        WHERE completed = 1 
        ORDER By taskId ASC
    """)
    fun completedTask(): Flow<List<Task>>


    @Query("""
        SELECT * FROM task 
        WHERE completed = 0 
        ORDER BY deadLine ASC
    """)
    fun activeTaskOrderByDeadLine(): Flow<List<Task>>

    @Query("""
        SELECT * FROM task 
        WHERE taskID = :id
        ORDER BY taskId ASC
    """)
    fun getTask(id: Int): Flow<Task?>

    @Query("""
        UPDATE task 
        set completed = 1 
        WHERE taskId = :taskId
    """)
    fun markCompleted(taskId: Int)

    @Query("""
        Update task 
        set completed = 0 
        WHERE taskId = :taskId
    """)
    fun unmarkCompleted(taskId: Int)

}
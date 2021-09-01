package br.com.todolistprojetofinal.repository

import androidx.lifecycle.LiveData
import br.com.todolistprojetofinal.data.dbRoom.entity.TaskEntity

interface TaskRepository {

    suspend fun insertTask(title: String, date: String, hour: String, description: String): Long

    suspend fun updateTask(id: Long, title: String, date: String, hour: String, description: String)

    suspend fun deleteTask(id: Long)

    suspend fun getAllTasks(): LiveData<List<TaskEntity>>
}
package br.com.todolistprojetofinal.data.dbRoom.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.todolistprojetofinal.data.dbRoom.entity.TaskEntity

@Dao
interface TaskDAO {
    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM db_task")
    fun getAll(): LiveData<List<TaskEntity>>

    @Query("DELETE FROM db_task WHERE id = :id")
    suspend fun delete(id: Long)
}
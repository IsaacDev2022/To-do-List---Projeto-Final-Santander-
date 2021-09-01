package br.com.todolistprojetofinal.data.dbRoom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var date: String,
    var hour: String,
    var description: String
)
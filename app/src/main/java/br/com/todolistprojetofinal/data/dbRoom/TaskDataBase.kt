package br.com.todolistprojetofinal.data.dbRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.todolistprojetofinal.data.dbRoom.dao.TaskDAO
import br.com.todolistprojetofinal.data.dbRoom.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {

    abstract val taskDAO: TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null

        fun getIntance(context: Context): TaskDataBase {
            synchronized(this) {
                var instance: TaskDataBase? = null
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        TaskDataBase::class.java,
                        "task_database"
                    ).build()
                }

                return instance
            }
        }
    }
}
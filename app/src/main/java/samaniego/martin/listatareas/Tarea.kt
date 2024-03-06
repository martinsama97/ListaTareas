package samaniego.martin.listatareas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey (autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "desc") var desc: String,
)

package samaniego.martin.listatareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tareas")
    fun obtenerTareas(): List<Tarea>

    @Query("SELECT * FROM tareas WHERE `desc` = :descripcion")
    fun getTarea(descripcion: String): Tarea

    @Insert
    fun agregarTarea(tarea: Tarea)

    @Delete
    fun eliminarTarea(vararg tarea: Tarea)

    @Update
    fun editarTarea(vararg tarea: Tarea)

}
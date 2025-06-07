package com.example.juansuarez_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    @Upsert()
    suspend fun save(tarea: TareaEntity)

    @Query(
        """
        SELECT * 
        FROM Tareas 
        WHERE tareaId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): TareaEntity?

    @Delete
    suspend fun delete(tarea: TareaEntity)

    @Query("SELECT * FROM Tareas")
    fun getAll(): Flow<List<TareaEntity>>

    @Query("SELECT COUNT(*) > 0 FROM Tareas WHERE LOWER(descripcion) = LOWER(:descripcion)")
    suspend fun existeDescripcion(descripcion: String): Boolean

}

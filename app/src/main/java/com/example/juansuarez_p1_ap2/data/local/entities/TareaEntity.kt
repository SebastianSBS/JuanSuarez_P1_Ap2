package com.example.juansuarez_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tareas")
data class TareaEntity(
    @PrimaryKey
    val tareaId: Int? = null,
    val descripcion: String = "",
    val tiempo: Int? = null
)


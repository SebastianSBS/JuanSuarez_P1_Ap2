package com.example.juansuarez_p1_ap2.presentation.tarea

import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String? = "",
    val tiempo: Int? = null,
    val errorMessage: String? = null,
    val tareas: List<TareaEntity> = emptyList()
)
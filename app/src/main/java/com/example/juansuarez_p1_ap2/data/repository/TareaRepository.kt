package com.example.juansuarez_p1_ap2.data.repository

import com.example.juansuarez_p1_ap2.data.local.dao.TareaDao
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareaDao: TareaDao
) {
    suspend fun save(tarea: TareaEntity) = tareaDao.save(tarea)

    suspend fun getTarea(id: Int) = tareaDao.find(id)

    suspend fun delete(tarea: TareaEntity) = tareaDao.delete(tarea)

    fun getTareas() = tareaDao.getAll()
}

package com.example.juansuarez_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.juansuarez_p1_ap2.data.local.dao.TareaDao
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity

@Database(
    entities = [
        TareaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TareaDb : RoomDatabase() {
    abstract fun TareaDao(): TareaDao
    }


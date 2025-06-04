package com.example.juansuarez_p1_ap2.di

import android.content.Context
import androidx.room.Room
import com.example.juansuarez_p1_ap2.data.local.database.TareaDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTareaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            TareaDb::class.java,
            "Tareas.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTareaDao(tareaDb: TareaDb) = tareaDb.TareaDao()

}

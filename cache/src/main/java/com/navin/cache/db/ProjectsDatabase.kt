package com.navin.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.navin.cache.dao.CachedProjectsDao
import com.navin.cache.dao.ConfigDao
import com.navin.cache.model.CachedProject
import com.navin.cache.model.Config
import javax.inject.Inject

@Database(entities = arrayOf(CachedProject::class, Config::class), version = 1)
abstract class ProjectsDatabase @Inject constructor(): RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao

    abstract fun configDao(): ConfigDao

    companion object {
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any() //giving me boolean values to decide

        fun getInstance(context: Context): ProjectsDatabase {

            if (INSTANCE == null){
                synchronized(lock){
                    if (INSTANCE == null){

                        INSTANCE = Room.databaseBuilder(context.applicationContext, ProjectsDatabase::class.java,
                                "projects.db")
                                .build()
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }

            return INSTANCE as ProjectsDatabase
        }
    }

}
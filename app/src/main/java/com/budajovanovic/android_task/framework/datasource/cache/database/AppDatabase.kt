package com.budajovanovic.android_task.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budajovanovic.android_task.framework.datasource.cache.model.PostCacheEntity

@Database(entities = [PostCacheEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }

}
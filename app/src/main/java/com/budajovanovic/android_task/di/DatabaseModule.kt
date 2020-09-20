package com.budajovanovic.android_task.di

import android.content.Context
import androidx.room.Room
import com.budajovanovic.android_task.framework.datasource.cache.database.AppDatabase
import com.budajovanovic.android_task.framework.datasource.cache.database.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDAO(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }
}
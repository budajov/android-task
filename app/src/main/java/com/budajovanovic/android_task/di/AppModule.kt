package com.budajovanovic.android_task.di

import android.content.Context
import com.budajovanovic.android_task.framework.datasource.network.util.NetworkConnectionProvider
import com.budajovanovic.android_task.framework.datasource.cache.util.SharedPreferencesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesProvider(@ApplicationContext context: Context): SharedPreferencesProvider {
        val sharedPreferencesProvider = SharedPreferencesProvider(context = context)
        sharedPreferencesProvider.init()
        return sharedPreferencesProvider
    }

    @Singleton
    @Provides
    fun provideNetworkConnectionProvider(@ApplicationContext context: Context): NetworkConnectionProvider {
        return NetworkConnectionProvider(context = context)
    }

}
package com.budajovanovic.android_task.di

import com.budajovanovic.android_task.framework.datasource.network.util.NetworkConnectionProvider
import com.budajovanovic.android_task.framework.datasource.network.services.NetworkService
import com.budajovanovic.android_task.framework.datasource.cache.database.PostDao
import com.budajovanovic.android_task.framework.datasource.cache.mappers.PostCacheEntityMapper
import com.budajovanovic.android_task.framework.datasource.cache.util.SharedPreferencesProvider
import com.budajovanovic.android_task.framework.datasource.network.mappers.PostNetworkEntityMapper
import com.budajovanovic.android_task.framework.datasource.network.mappers.UserNetworkEntityMapper
import com.budajovanovic.android_task.framework.ui.details.PostDetailsRepository
import com.budajovanovic.android_task.framework.ui.post.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        networkService: NetworkService,
        postDao: PostDao,
        postCacheMapper: PostCacheEntityMapper,
        postNetworkMapper: PostNetworkEntityMapper,
        sharedPreferencesProvider: SharedPreferencesProvider,
        networkConnectionProvider: NetworkConnectionProvider
    ): PostsRepository {
        return PostsRepository(
            networkService = networkService,
            postDao = postDao,
            postCacheMapper = postCacheMapper,
            postNetworkMapper = postNetworkMapper,
            sharedPreferencesProvider = sharedPreferencesProvider,
            networkConnectionProvider = networkConnectionProvider
        )
    }

    @Singleton
    @Provides
    fun providePostDetailsRepository(
        networkService: NetworkService,
        userNetworkEntityMapper: UserNetworkEntityMapper,
        networkConnectionProvider: NetworkConnectionProvider
    ): PostDetailsRepository {
        return PostDetailsRepository(
            networkService = networkService,
            userNetworkEntityMapper = userNetworkEntityMapper,
            networkConnectionProvider = networkConnectionProvider
        )
    }
}
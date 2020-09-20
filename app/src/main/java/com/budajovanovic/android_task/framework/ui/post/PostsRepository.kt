package com.budajovanovic.android_task.framework.ui.post

import com.budajovanovic.android_task.DEFAULT_ERROR_MSG
import com.budajovanovic.android_task.POST_VALIDITY_TIME_FIVE_MINUTES_IN_MS
import com.budajovanovic.android_task.framework.datasource.network.services.NetworkService
import com.budajovanovic.android_task.framework.datasource.cache.model.PostCacheEntity
import com.budajovanovic.android_task.framework.datasource.cache.database.PostDao
import com.budajovanovic.android_task.business.model.Post
import com.budajovanovic.android_task.business.state.DataState
import com.budajovanovic.android_task.framework.datasource.cache.mappers.PostCacheEntityMapper
import com.budajovanovic.android_task.framework.datasource.network.util.NetworkConnectionProvider
import com.budajovanovic.android_task.framework.datasource.network.mappers.PostNetworkEntityMapper
import com.budajovanovic.android_task.framework.datasource.cache.util.SharedPreferencesProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class PostsRepository
constructor(
    private val networkService: NetworkService,
    private val postDao: PostDao,
    private val postCacheMapper: PostCacheEntityMapper,
    private val postNetworkMapper: PostNetworkEntityMapper,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val networkConnectionProvider: NetworkConnectionProvider

) {

    private var posts: ArrayList<Post> = ArrayList()

    suspend fun removePostLocally(post: Post): Flow<DataState<List<Post>>> = flow {
        try {
            posts.remove(post)
            postDao.deletePost(postCacheMapper.mapToEntity(post))
            emit(DataState.Success(posts))
        } catch (e: Exception) {
            emit(DataState.Error(DEFAULT_ERROR_MSG, e))
        }
    }

    suspend fun getPosts(refresh: Boolean): Flow<DataState<List<Post>>> = flow {
        emit(DataState.Loading)
        try {
            var cachedPosts: List<PostCacheEntity>
            if (refresh) {
                if (sharedPreferencesProvider.postValidityTime() >= Date().time && !networkConnectionProvider.isOnline()) {
                    //do nothing
                    Timber.d("Valid post time and no internet connection")
                    cachedPosts = postDao.getAllPosts()
                    emit(DataState.NoInternetConnection)

                    if (cachedPosts.isNotEmpty()) {
                        posts.clear()
                        posts.addAll(postCacheMapper.mapFromEntityList(cachedPosts))
                        emit(DataState.Success(posts))
                        return@flow
                    }
                } else {
                    Timber.d("Refreshing posts")
                    postDao.deleteAllPosts()
                    sharedPreferencesProvider.clearPostValidityTime()
                }

            } else if (sharedPreferencesProvider.postValidityTime() < Date().time) {
                Timber.d("Invalid post time")
                postDao.deleteAllPosts()
                sharedPreferencesProvider.clearPostValidityTime()
            } else if (sharedPreferencesProvider.postValidityTime() >= Date().time) {
                Timber.d("Valid post time")
                cachedPosts = postDao.getAllPosts()
                if (cachedPosts.isNotEmpty()) {
                    posts.clear()
                    posts.addAll(postCacheMapper.mapFromEntityList(cachedPosts))
                    emit(DataState.Success(posts))
                    return@flow
                }
            }

            if (networkConnectionProvider.isOnline()) {
                val networkPosts = networkService.getPosts()
                sharedPreferencesProvider.setPostValidityTime(Date().time + POST_VALIDITY_TIME_FIVE_MINUTES_IN_MS)

                postDao.insertAll(
                    postCacheMapper.mapToEntityList(
                        postNetworkMapper.mapFromEntityList(
                            networkPosts
                        )
                    )
                )
            } else {
                emit(DataState.NoInternetConnection)
            }


            cachedPosts = postDao.getAllPosts()
            posts.clear()
            posts.addAll(postCacheMapper.mapFromEntityList(cachedPosts))
            emit(DataState.Success(posts))
        } catch (e: Exception) {
            emit(DataState.Error(DEFAULT_ERROR_MSG, e))
        }
    }
}
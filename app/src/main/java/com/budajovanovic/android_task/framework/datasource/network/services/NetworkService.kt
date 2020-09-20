package com.budajovanovic.android_task.framework.datasource.network.services

import com.budajovanovic.android_task.framework.datasource.network.model.PostNetworkEntity
import com.budajovanovic.android_task.framework.datasource.network.model.UserNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("posts")
    suspend fun getPosts(): List<PostNetworkEntity>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserNetworkEntity
}
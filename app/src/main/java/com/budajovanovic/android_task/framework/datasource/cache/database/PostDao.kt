package com.budajovanovic.android_task.framework.datasource.cache.database

import androidx.room.*
import com.budajovanovic.android_task.framework.datasource.cache.model.PostCacheEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postEntity: PostCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<PostCacheEntity>)

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostCacheEntity>

    @Delete
    suspend fun deletePost(postEntity: PostCacheEntity)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()
}
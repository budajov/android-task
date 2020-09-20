package com.budajovanovic.android_task.framework.datasource.cache.mappers

import com.budajovanovic.android_task.framework.datasource.cache.model.PostCacheEntity
import com.budajovanovic.android_task.business.model.Post
import com.budajovanovic.android_task.business.util.EntityMapper
import javax.inject.Inject

class PostCacheEntityMapper
@Inject
constructor() : EntityMapper<PostCacheEntity, Post> {

    override fun mapFromEntity(entity: PostCacheEntity): Post {
        return Post(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            userId = entity.userId
        )
    }

    override fun mapToEntity(domainModel: Post): PostCacheEntity {
        return PostCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            userId = domainModel.userId
        )
    }

    override fun mapFromEntityList(entities: List<PostCacheEntity>): List<Post> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(entities: List<Post>): List<PostCacheEntity> {
        return entities.map { mapToEntity(it) }
    }

}
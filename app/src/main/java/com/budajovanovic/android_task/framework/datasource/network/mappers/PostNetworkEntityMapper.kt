package com.budajovanovic.android_task.framework.datasource.network.mappers

import com.budajovanovic.android_task.framework.datasource.network.model.PostNetworkEntity
import com.budajovanovic.android_task.business.model.Post
import com.budajovanovic.android_task.business.util.EntityMapper
import javax.inject.Inject

class PostNetworkEntityMapper
@Inject
constructor() : EntityMapper<PostNetworkEntity, Post> {

    override fun mapFromEntity(entity: PostNetworkEntity): Post {
        return Post(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            userId = entity.userId
        )
    }

    override fun mapToEntity(domainModel: Post): PostNetworkEntity {
        return PostNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            userId = domainModel.userId
        )
    }

    override fun mapFromEntityList(entities: List<PostNetworkEntity>): List<Post> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(entities: List<Post>): List<PostNetworkEntity> {
        return entities.map { mapToEntity(it) }
    }
}
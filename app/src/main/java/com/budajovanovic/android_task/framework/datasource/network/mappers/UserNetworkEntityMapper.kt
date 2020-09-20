package com.budajovanovic.android_task.framework.datasource.network.mappers

import com.budajovanovic.android_task.framework.datasource.network.model.UserNetworkEntity
import com.budajovanovic.android_task.business.model.User
import com.budajovanovic.android_task.business.util.EntityMapper
import javax.inject.Inject

class UserNetworkEntityMapper
@Inject
constructor() : EntityMapper<UserNetworkEntity, User> {
    override fun mapFromEntity(entity: UserNetworkEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            email = entity.email
        )
    }

    override fun mapToEntity(domainModel: User): UserNetworkEntity {
        return UserNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            email = domainModel.email
        )
    }

    override fun mapFromEntityList(entities: List<UserNetworkEntity>): List<User> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(entities: List<User>): List<UserNetworkEntity> {
        return entities.map { mapToEntity(it) }
    }

}
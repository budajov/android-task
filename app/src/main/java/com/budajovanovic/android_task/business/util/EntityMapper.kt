package com.budajovanovic.android_task.business.util

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapFromEntityList(entities: List<Entity>): List<DomainModel>

    fun mapToEntityList(entities: List<DomainModel>): List<Entity>
}
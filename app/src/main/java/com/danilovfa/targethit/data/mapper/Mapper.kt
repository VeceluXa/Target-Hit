package com.danilovfa.targethit.data.mapper

interface Mapper<Entity, Domain> {
    fun fromEntity(entity: Entity): Domain
    fun fromDomain(domain: Domain): Entity
}
package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.EpisodeModel

fun EpisodeEntity.toEpisodeDomainModel(): EpisodeModel {
    return EpisodeModel(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
}

fun EpisodeModel.toDbEntity() : EpisodeEntity {
    return EpisodeEntity(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
}
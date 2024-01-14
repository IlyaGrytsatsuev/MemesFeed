package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.utils.NullReceivedException

fun EpisodeEntity?.toEpisodeDomainModel(): EpisodeModel {
    if(this == null)
        throw NullReceivedException()
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

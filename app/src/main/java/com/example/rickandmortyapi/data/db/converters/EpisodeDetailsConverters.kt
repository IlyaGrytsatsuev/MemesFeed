package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel

fun EpisodeDetailsModel.toCharacterWithEpisodesDbModel() : EpisodeWithCharacters {
    val characters = this.characters.map {
        it.toDbEntity()
    }
    val resultEpisode =  EpisodeEntity(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
    return  EpisodeWithCharacters(
        episodeEntity = resultEpisode,
        characters = characters
    )
}

fun EpisodeWithCharacters?.toEpisodeDetailsModel(): EpisodeDetailsModel {
    val characters = this?.characters?.map {
        it.toCharacterModel()
    }
    val episode = this?.episodeEntity

    return EpisodeDetailsModel(
        id = episode?.id ?: 0,
        isNullReceived = this == null,
        name = episode?.name ?: "",
        episode = episode?.episode ?: "",
        charactersIds = emptyList(),
        characters = characters ?: emptyList()
    )
}
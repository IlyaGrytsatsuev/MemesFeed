package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.utils.NullReceivedException

fun EpisodeDetailsModel.toEpisodeWithCharactersDbModel() : EpisodeWithCharacters {
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
    if(this == null)
        throw NullReceivedException()
    val characters = this.characters.map {
        it.toCharacterModel()
    }
    val episode = this.episodeEntity

    return EpisodeDetailsModel(
        id = episode.id,
        name = episode.name,
        episode = episode.episode,
        charactersIds = emptyList(),
        characters = characters,
        listSize = characters.size
    )
}
package com.example.rickandmortyapi.data.db.converters


import android.util.Log
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodes
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.utils.NullReceivedException

fun CharacterDetailsModel.toEpisodeWithCharactersDbModel() : CharacterWithEpisodes {
    val episodes = this.episode.map {
        it.toDbEntity()
    }
    val resultCharacter = CharacterEntity(
        created = this.created,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = location.toDBModel(),
        name = this.name,
        origin = origin.toDBModel(),
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
    )
    return  CharacterWithEpisodes(
        characterEntity = resultCharacter,
        episodes = episodes
    )
}

fun CharacterWithEpisodes?.toCharacterDetailsModel(): CharacterDetailsModel {
    if(this == null)
        throw NullReceivedException()
    val character = this.characterEntity
    val episodes = this.episodes.map {
        it.toEpisodeDomainModel()
    }
    return CharacterDetailsModel(
        created = character.created,
        episodeIds = emptyList(),
        episode = episodes,
        gender = character.gender,
        id = character.id,
        image = character.image,
        location = character.location.toDomainModel(),
        name = character.name,
        origin = character.origin.toDomainModel(),
        species = character.species,
        status = character.status,
        type = character.type,
        url = character.url,
        listSize = episodes.size
    )
}
package com.example.rickandmortyapi.data.db.converters


import android.util.Log
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodes
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel

fun CharacterDetailsModel.toEpisodeWithCharactersDbModel() : CharacterWithEpisodes {
    val episodes = mutableListOf<EpisodeEntity>()
    this.episode.forEach {
        episodes.add(it.toDbEntity())
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
    val character = this?.characterEntity
    var episodes = this?.episodes?.map {
        it.toEpisodeDomainModel()
    }?: emptyList()
    return CharacterDetailsModel(
        created = character?.created?:"",
        isNullReceived = this == null,
        episodeIds = emptyList(),
        episode = episodes,
        gender = character?.gender?:"",
        id = character?.id?:0,
        image = character?.image?:"",
        location = character?.location?.toDomainModel()
            ?: CharacterModelLocation(),
        name = character?.name?:"",
        origin = character?.origin?.toDomainModel()
            ?: CharacterModelOrigin(),
        species = character?.species?:"",
        status = character?.status?:"",
        type = character?.type?:"",
        url = character?.url?:""
    )
}
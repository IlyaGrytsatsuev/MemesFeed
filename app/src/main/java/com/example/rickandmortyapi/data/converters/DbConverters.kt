package com.example.rickandmortyapi.data.converters

import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodesUrls
import com.example.rickandmortyapi.data.db.entities.EpisodeUrlEntity
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterOrigin
import com.example.rickandmortyapi.domain.models.CharacterLocation

fun CharacterWithEpisodesUrls.toCharacterModel() : CharacterModel{
    val character = this.characterEntity
    val episodesList = this.episodesUrls
    return CharacterModel(
        created = character.created,
        episode = episodesList.toStringList(),
        gender = character.gender,
        id = character.id,
        image = character.image,
        location = character.location.toDomainModel(),
        name = character.name,
        origin = character.origin.toDomainModel(),
        species = character.species,
        status = character.status,
        type = character.type,
        url = character.url
    )
}

fun List<EpisodeUrlEntity>.toStringList():List<String>{
    val result = mutableListOf<String>()
    this.forEach {
        result.add(it.url)
    }
    return result
}

fun List<String>.toEpisodeUrlEntitiesList():List<EpisodeUrlEntity>{
    val result = mutableListOf<EpisodeUrlEntity>()
    this.forEach {
        result.add(EpisodeUrlEntity(it))
    }
    return result
}

fun com.example.rickandmortyapi.data.db.entities.CharacterLocation
    .toDomainModel():
        CharacterLocation =
    CharacterLocation(this.name, this.url)

fun CharacterLocation
        .toDBModel():
        com.example.rickandmortyapi.data.db.entities.CharacterLocation =
    com.example.rickandmortyapi.data.db.entities.CharacterLocation(this.name, this.url)


fun com.example.rickandmortyapi.data.db.entities.CharacterOrigin
    .toDomainModel() : CharacterOrigin =
    CharacterOrigin(this.name, this.url)

fun CharacterOrigin
        .toDBModel() :
        com.example.rickandmortyapi.data.db.entities.CharacterOrigin =
    com.example.rickandmortyapi.data.db.entities.CharacterOrigin(this.name, this.url)


fun CharacterModel.toDbEntity() : CharacterWithEpisodesUrls{
    val dbCharacterLocation = this.location.toDBModel()
    val dbCharacterOrigin = this.origin.toDBModel()
    val characterEntity = CharacterEntity(
        created = this.created,
        gender = this.gender,
        id = this.id,
        image = this.image,
        location = dbCharacterLocation,
        name = this.name,
        origin = dbCharacterOrigin,
        species = this.species,
        status = this.status,
        type = this.type,
        url = this.url
    )
    return CharacterWithEpisodesUrls(characterEntity,
        this.episode.toEpisodeUrlEntitiesList())

}

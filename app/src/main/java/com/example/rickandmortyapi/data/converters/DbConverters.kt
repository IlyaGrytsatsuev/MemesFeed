package com.example.rickandmortyapi.data.converters

import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodes
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.EpisodeModel

fun CharacterEntity.toCharacterModel() : CharacterModel{
    val character = this
    return CharacterModel(
        created = character.created,
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

fun CharacterDetailsModel.toCharacterWithEpisodesDbModel() :CharacterWithEpisodes{
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

fun CharacterWithEpisodes.toCharacterDetailsModel(): CharacterDetailsModel{
    val episodes = mutableListOf<EpisodeModel>()
    val character = this.characterEntity
    this.episodes.forEach {
        episodes.add(it.toEpisodeDomainModel())
    }
    return  CharacterDetailsModel(
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
        url = character.url
    )
}

fun EpisodeEntity.toEpisodeDomainModel():EpisodeModel{
    return EpisodeModel(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
}

fun EpisodeModel.toDbEntity() :EpisodeEntity{
    return EpisodeEntity(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
}

fun List<EpisodeEntity>.toIdsList():List<Int>{
    val result = mutableListOf<Int>()
    this.forEach {
        result.add(it.id)
    }
    return result
}

fun List<Int>.toEpisodeEntitiesList():List<EpisodeEntity>{
    val result = mutableListOf<EpisodeEntity>()
    this.forEach {
        result.add(EpisodeEntity(it))
    }
    return result
}

fun com.example.rickandmortyapi.data.db.entities.CharacterLocation
    .toDomainModel():
        CharacterModelLocation =
    CharacterModelLocation(this.name, this.url)

fun CharacterModelLocation
        .toDBModel():
        com.example.rickandmortyapi.data.db.entities.CharacterLocation =
    com.example.rickandmortyapi.data.db.entities.CharacterLocation(this.name, this.url)


fun com.example.rickandmortyapi.data.db.entities.CharacterOrigin
    .toDomainModel() : CharacterModelOrigin =
    CharacterModelOrigin(this.name, this.url)

fun CharacterModelOrigin
        .toDBModel() :
        com.example.rickandmortyapi.data.db.entities.CharacterOrigin =
    com.example.rickandmortyapi.data.db.entities.CharacterOrigin(this.name, this.url)


fun CharacterModel.toDbEntity() : CharacterEntity{
    val dbCharacterLocation = this.location.toDBModel()
    val dbCharacterOrigin = this.origin.toDBModel()
    return CharacterEntity(
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


}

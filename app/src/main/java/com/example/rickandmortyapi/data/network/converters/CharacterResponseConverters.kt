package com.example.rickandmortyapi.data.network.converters

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.ResponseLocation
import com.example.rickandmortyapi.data.network.responseModels.ResponseOrigin
import com.example.rickandmortyapi.data.network.responseModels.SingleCharacterResponse
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.utils.NullReceivedException

fun CharactersResponse?.toDomainCharactersModelsList(): List<CharacterModel> {
    if(this == null)
        throw NullReceivedException()
    val result = this.results?.map { result1 ->
        CharacterModel(
            created = result1.created?:"",
            gender = result1.gender?:"",
            id = result1.id?:0,
            image = result1.image?:"",
            location = result1.responseLocation?.toCharacterLocation()
                ?: CharacterModelLocation.newEmptyInstance(),
            name = result1.name?:"",
            origin = result1.responseOrigin?.toCharacterOrigin()
                ?: CharacterModelOrigin.newEmptyInstance(),
            species = result1.species?:"",
            status = result1.status?:"",
            type = result1.type?:"",
            url = result1.url?:""
        )
    }?: emptyList()
    return result
}

fun SingleCharacterResponse?.toCharacterDetailsDomainModel(): CharacterDetailsModel {

    val episodeIds = this?.episode?.map {
        it.substringAfterLast("/")
            .toInt()
    }?: emptyList()

    if(this == null)
        throw NullReceivedException()

    return  CharacterDetailsModel(
        created = this.created?:"",
        episodeIds = episodeIds,
        episode = mutableListOf(),
        gender = this.gender?:"",
        id = this.id?:0,
        image = this.image?:"",
        location = this.responseLocation?.toCharacterLocation()
            ?: CharacterModelLocation("",""),
        name = this.name?:"",
        origin = this.responseOrigin?.toCharacterOrigin()
            ?: CharacterModelOrigin("",""),
        species = this.species?:"",
        status = this.status?:"",
        type = this.type?:"",
        url = this.url?:"",
        listSize = episodeIds.size
    )
}

fun SingleCharacterResponse?.toCharacterModel(): CharacterModel {
    if(this == null)
        throw NullReceivedException()
    return CharacterModel(
        created = this.created?:"",
        gender = this.gender?:"",
        id = this.id?:0,
        image = this.image?:"",
        location = this.responseLocation?.toCharacterLocation()
            ?: CharacterModelLocation("",""),
        name = this.name?:"",
        origin = this.responseOrigin?.toCharacterOrigin()
            ?: CharacterModelOrigin("",""),
        species = this.species?:"",
        status = this.status?:"",
        type = this.type?:"",
        url = this.url?:""
    )
}

fun CharacterDetailsModel.appendEpisodesDetails(episodes:List<EpisodeModel>){
    this.episode = episodes.toList()
}

fun ResponseLocation?.toCharacterLocation(): CharacterModelLocation {
    if(this == null)
        throw NullReceivedException()
    return CharacterModelLocation(
        name = this.name ?: "",
        url = this.url ?: ""
    )
}

fun ResponseOrigin?.toCharacterOrigin(): CharacterModelOrigin {
    if(this == null)
        throw NullReceivedException()
    return CharacterModelOrigin(
        name = this.name ?: "",
        url = this.url ?: ""
    )
}

fun CharactersResponse?.getPagesNum() = this?.info?.pages ?: 0

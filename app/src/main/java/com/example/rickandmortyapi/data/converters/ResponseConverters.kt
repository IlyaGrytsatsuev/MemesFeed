package com.example.rickandmortyapi.data.converters

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.Info
import com.example.rickandmortyapi.data.network.responseModels.Location
import com.example.rickandmortyapi.data.network.responseModels.Origin
import com.example.rickandmortyapi.domain.models.CharacterLocation
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterOrigin
import com.example.rickandmortyapi.domain.models.InfoModel

fun CharactersResponse.toDomainCharactersModelsList(): MutableList<CharacterModel> {
    val result: MutableList<CharacterModel> = mutableListOf()
    this.results?.forEach {
        val characterModel = CharacterModel(
            created = it.created?:"",
            episode = it.episode?: emptyList(),
            gender = it.gender?:"",
            id = it.id?:0,
            image = it.image?:"",
            location = it.location?.toCharacterLocation()
                ?:CharacterLocation("",""),
            name = it.name?:"",
            origin = it.origin?.toCharacterOrigin()
                ?:CharacterOrigin("",""),
            species = it.species?:"",
            status = it.status?:"",
            type = it.type?:"",
            url = it.url?:""
        )
        result.add(characterModel)
    }
    //Log.d("respBody", result.toString())
    return result
}

fun Location.toCharacterLocation(): CharacterLocation {
    return CharacterLocation(
        name = this.name ?: "",
        url = this.url ?: ""
    )
}

fun Origin.toCharacterOrigin(): CharacterOrigin =
    CharacterOrigin(
        name = this.name?:"",
        url = this.url?:""
    )

fun Info.toDomainModel(): InfoModel =
    InfoModel(count = this.count?:0,
        next = this.next?:"",
        pages = this.pages?:0,
        prev = this.prev?:"")


package com.example.rickandmortyapi.data.converters

import android.util.Log
import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.Info
import com.example.rickandmortyapi.data.network.responseModels.Location
import com.example.rickandmortyapi.data.network.responseModels.Origin
import com.example.rickandmortyapi.data.network.responseModels.SingleCharacterResponse
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.InfoModel

fun CharactersResponse.toDomainCharactersModelsList(): List<CharacterModel> {
    val result: MutableList<CharacterModel> = mutableListOf()
    this.results?.forEach { result1 ->
        val characterModel = CharacterModel(
            created = result1.created?:"",
            //episode = episodeIndexes,
            gender = result1.gender?:"",
            id = result1.id?:0,
            image = result1.image?:"",
            location = result1.location?.toCharacterLocation()
                ?:CharacterModelLocation("",""),
            name = result1.name?:"",
            origin = result1.origin?.toCharacterOrigin()
                ?:CharacterModelOrigin("",""),
            species = result1.species?:"",
            status = result1.status?:"",
            type = result1.type?:"",
            url = result1.url?:""
        )
        result.add(characterModel)
    }
    return result
}

fun SingleCharacterResponse.toCharacterDetailsDomainModel(): CharacterDetailsModel {

    val episodeIds = mutableListOf<Int>()
    this.episode?.forEach {
        val idStr = it.substringAfterLast("/")
        episodeIds.add(idStr.toInt())
    }
    return  CharacterDetailsModel(
            created = this.created?:"",
            episodeIds = episodeIds,
            episode = mutableListOf(),
            gender = this.gender?:"",
            id = this.id?:0,
            image = this.image?:"",
            location = this.location?.toCharacterLocation()
                ?:CharacterModelLocation("",""),
            name = this.name?:"",
            origin = this.origin?.toCharacterOrigin()
                ?:CharacterModelOrigin("",""),
            species = this.species?:"",
            status = this.status?:"",
            type = this.type?:"",
            url = this.url?:""
        )
}

fun EpisodeResponse.toEpisodeDomainModel() : EpisodeModel{

    Log.d("listnet", "episodeModel = $this")
    return EpisodeModel(id = this.id?:0,
        name = this.name?:"",
        episode = this.episode?:"",
        )
}
fun CharacterDetailsModel.appendEpisodesDetails(episodes:List<EpisodeModel>){
    episodes.forEach {
        this.episode.add(it)
    }

}

fun Location.toCharacterLocation(): CharacterModelLocation {
    return CharacterModelLocation(
        name = this.name ?: "",
        url = this.url ?: ""
    )
}

fun Origin.toCharacterOrigin(): CharacterModelOrigin =
    CharacterModelOrigin(
        name = this.name?:"",
        url = this.url?:""
    )

fun CharactersResponse.getPagesNum() = this.info?.pages ?: 0
fun Info.toDomainModel(): InfoModel =
    InfoModel(count = this.count?:0,
        next = this.next?:"",
        pages = this.pages?:0,
        prev = this.prev?:"")


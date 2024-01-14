package com.example.rickandmortyapi.data.network.converters

import android.util.Log
import com.example.rickandmortyapi.data.network.responseModels.SingleEpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.utils.NullReceivedException

fun SingleEpisodeResponse?.toEpisodeDomainModel() : EpisodeModel {
    if(this == null)
        throw NullReceivedException()

    return EpisodeModel(id = this.id?:0,
        name = this.name?:"",
        episode = this.episode?:"",
    )
}

fun SingleEpisodeResponse?.toEpisodeDetailsModel(): EpisodeDetailsModel{
    if(this == null)
        throw NullReceivedException()

    val idsList:List<Int> = this.characters?.map {
        it.substringAfterLast("/").toInt()
    } ?: emptyList()
    return EpisodeDetailsModel(
        id = this.id ?: 0 ,
        name = this.name ?: "",
        episode = this.episode ?:"",
        charactersIds = idsList,
        characters = emptyList(),
        listSize = idsList.size
    )
}

fun EpisodeDetailsModel.appendCharactersList(characterList: List<CharacterModel>)
: EpisodeDetailsModel{
    this.characters = characterList
    return this
}

fun EpisodesResponse?.toEpisodeModelList():List<EpisodeModel>{
    if(this == null)
        throw NullReceivedException()
    val episodesResponseList = this.results
    val resultList = episodesResponseList?.map {
        EpisodeModel(id = it.id?:0,
            name = it.name?:"",
            episode = it.episode?:"")
    }?: listOf()
    return resultList
}


fun EpisodesResponse?.getPagesNum() = this?.info?.pages ?: 0



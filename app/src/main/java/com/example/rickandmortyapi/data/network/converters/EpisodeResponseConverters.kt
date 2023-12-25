package com.example.rickandmortyapi.data.network.converters

import android.util.Log
import com.example.rickandmortyapi.data.network.responseModels.SingleEpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel

fun SingleEpisodeResponse.toEpisodeDomainModel() : EpisodeModel {

    Log.d("listnet", "episodeModel = $this")
    return EpisodeModel(id = this.id?:0,
        name = this.name?:"",
        episode = this.episode?:"",
    )
}

fun SingleEpisodeResponse.toEpisodeDetailsModel(): EpisodeDetailsModel{
    val idsList:List<Int> = this.characters?.map {
        it.substringAfterLast("/").toInt() } ?: emptyList()
    return EpisodeDetailsModel(
        id = this.id ?: 0 ,
        name = this.name ?: "",
        episode = this.episode ?:"",
        charactersIds = idsList,
        characters = emptyList()
    )
}

fun EpisodeDetailsModel.appendCharactersList(characterList: List<CharacterModel>)
: EpisodeDetailsModel{
    this.characters = characterList
    return this
}

fun EpisodesResponse.toEpisodeModelList():List<EpisodeModel>{
    val episodesResponseList = this.results
    val resultList = episodesResponseList?.map {
        EpisodeModel(id = it.id,
            name = it.name,
            episode = it.episode)
    }?: listOf()
    return resultList
}


fun EpisodesResponse.getPagesNum() = this.info?.pages ?: 0



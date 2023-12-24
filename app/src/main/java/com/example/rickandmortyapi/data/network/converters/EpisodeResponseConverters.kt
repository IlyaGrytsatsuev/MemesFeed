package com.example.rickandmortyapi.data.network.converters

import android.util.Log
import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import com.example.rickandmortyapi.domain.models.EpisodeModel

fun EpisodeResponse.toEpisodeDomainModel() : EpisodeModel {

    Log.d("listnet", "episodeModel = $this")
    return EpisodeModel(id = this.id?:0,
        name = this.name?:"",
        episode = this.episode?:"",
    )
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


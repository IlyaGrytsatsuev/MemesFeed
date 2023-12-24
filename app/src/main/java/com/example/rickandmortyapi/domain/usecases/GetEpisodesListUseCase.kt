package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import javax.inject.Inject

class GetEpisodesListUseCase @Inject
constructor(private val episodesApiRepository: EpisodesApiRepository){

    suspend fun execute() : List<EpisodeModel>{
        val result = episodesApiRepository.getEpisodesList()
        return result
    }
}
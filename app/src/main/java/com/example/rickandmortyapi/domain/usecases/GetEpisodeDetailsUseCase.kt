package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.repository.EpisodeDetailsApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.presenter.State
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val episodeDetailsApiRepository: EpisodeDetailsApiRepository
) {

    suspend fun execute(id:Int): EpisodeDetailsModel {
        val result = episodeDetailsApiRepository.getEpisodeDetails(id)
        return result
    }
}
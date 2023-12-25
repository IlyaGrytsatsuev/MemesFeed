package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.presenter.State
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(
    private val episodesApiRepository: EpisodesApiRepository
) {

    suspend fun execute(id:Int): State<EpisodeDetailsModel?> {
        val result = episodesApiRepository.getEpisodeDetails(id)
        return result
    }
}
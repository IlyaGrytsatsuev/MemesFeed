package com.example.rickandmortyapi.data.network.repository

import com.example.rickandmortyapi.data.db.converters.toEpisodeWithCharactersDbModel
import com.example.rickandmortyapi.data.network.converters.appendCharactersList
import com.example.rickandmortyapi.data.network.converters.toEpisodeDetailsModel
import com.example.rickandmortyapi.data.network.service.EpisodesApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodeDetailsApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import com.example.rickandmortyapi.utils.NullReceivedException
import javax.inject.Inject

class EpisodeDetailsApiRepositoryImpl @Inject
constructor(private val episodesApiService: EpisodesApiService,
            private val episodesDbRepository: EpisodesDbRepository,
            private val charactersApiRepository: CharactersApiRepository
):EpisodeDetailsApiRepository {

    override suspend fun getEpisodeDetailsModelById(id: Int): EpisodeDetailsModel {
        val response = episodesApiService.getEpisodeById(id)
        return response.toEpisodeDetailsModel()
    }

    override suspend fun getEpisodeDetails(id: Int): EpisodeDetailsModel {
        var episodeDetails : EpisodeDetailsModel
        val charactersList : List<CharacterModel>
        try{
            episodeDetails = getEpisodeDetailsModelById(id)

            charactersList = episodeDetails.charactersIds.map {
                charactersApiRepository.getCharacterModelById(it)
            }

            episodeDetails.appendCharactersList(charactersList)

            episodesDbRepository
                .upsertEpisodeWithCharactersIntoDb(episodeDetails)
        }
        catch (n:NullReceivedException){
            throw NullReceivedException()
        }
        catch (e:Exception){
             episodeDetails = episodesDbRepository
                .getEpisodeWithCharactersFromDB(id)
        }

        return episodeDetails
    }
}
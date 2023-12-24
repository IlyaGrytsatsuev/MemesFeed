package com.example.rickandmortyapi.data.db.repository

import com.example.rickandmortyapi.data.db.converters.toDbEntity
import com.example.rickandmortyapi.data.db.converters.toEpisodeDomainModel
import com.example.rickandmortyapi.data.db.dao.EpisodeDao
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.utils.Constants
import javax.inject.Inject

class EpisodesDbRepositoryImpl @Inject
constructor(private val episodeDao: EpisodeDao,
            private val paginationDataRepository: PaginationDataRepository
): EpisodesDbRepository {
    override suspend fun upsertEpisodesList(episodesList: List<EpisodeModel>) {
        val list = episodesList.map {
            it.toDbEntity()
        }
        episodeDao.upsertEpisodeEntities(list)
    }

    override suspend fun getEpisodesList(): List<EpisodeModel> {
        val limit = Constants.ITEMS_PER_PAGE
        val offset = limit*(paginationDataRepository.getCurPage()-1)
        val episodesDbList = episodeDao.getEpisodesEntitiesList(
            limit = limit,
            offset = offset)
        val episodesList = if(episodesDbList.isNotEmpty())
            episodesDbList.map { it.toEpisodeDomainModel() }
            else emptyList()
        return episodesList
    }
}
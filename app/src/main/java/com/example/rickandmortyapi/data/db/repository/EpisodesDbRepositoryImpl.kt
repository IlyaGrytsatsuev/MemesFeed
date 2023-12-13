package com.example.rickandmortyapi.data.db.repository

import com.example.rickandmortyapi.data.db.dao.EpisodeDao
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import javax.inject.Inject

class EpisodesDbRepositoryImpl @Inject
constructor(private val episodeDao: EpisodeDao): EpisodesDbRepository {
}
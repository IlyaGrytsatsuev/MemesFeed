package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.network.converters.toEpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import org.junit.Assert.*

import org.junit.Test

class EpisodeEntityConvertersKtTest {

    private lateinit var episodeEntity: EpisodeEntity

    private lateinit var episodeModel: EpisodeModel

    private fun initNullableData(){
        episodeEntity = EpisodeEntity()
        episodeModel = EpisodeModel()
    }

    private fun initRegularData(){
        episodeEntity = EpisodeEntity(2, "name", "episode")
        episodeModel = EpisodeModel(2, "name", "episode")
    }

    @Test
    fun entityToEpisodeDomainModelWithNullableDataTest() {
        initNullableData()
        val executionResult = episodeEntity.toEpisodeDomainModel()
        assertEquals(episodeModel, executionResult)
    }

    @Test
    fun entityToEpisodeDomainModelWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeEntity.toEpisodeDomainModel()
        assertEquals(episodeModel, executionResult)
    }

    @Test
    fun episodeDomainModelToDbEntityWithNullableDataTest() {
        initNullableData()
        val executionResult = episodeModel.toDbEntity()
        assertEquals(episodeEntity, executionResult)
    }

    @Test
    fun episodeDomainModelToDbEntityWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeModel.toDbEntity()
        assertEquals(episodeEntity, executionResult)
    }
}
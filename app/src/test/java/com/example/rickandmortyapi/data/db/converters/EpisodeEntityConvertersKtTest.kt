package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.network.converters.toEpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.utils.NullReceivedException
import org.junit.Assert.*

import org.junit.Test

class EpisodeEntityConvertersKtTest {

    private var episodeEntity: EpisodeEntity? = null

    private lateinit var episodeModel: EpisodeModel

    private fun initNullableData(){
        episodeEntity = null
        episodeModel = EpisodeModel.newEmptyInstance()
    }

    private fun initRegularData(){
        episodeEntity = EpisodeEntity(2, "name", "episode")
        episodeModel = EpisodeModel(2, "name", "episode")
    }

    @Test(expected = NullReceivedException::class)
    fun entityToEpisodeDomainModelWithNullableDataTest() {
        initNullableData()
        val executionResult = episodeEntity.toEpisodeDomainModel()
    }

    @Test
    fun entityToEpisodeDomainModelWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeEntity.toEpisodeDomainModel()
        assertEquals(episodeModel, executionResult)
    }


    @Test
    fun episodeDomainModelToDbEntityWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeModel.toDbEntity()
        assertEquals(episodeEntity, executionResult)
    }
}
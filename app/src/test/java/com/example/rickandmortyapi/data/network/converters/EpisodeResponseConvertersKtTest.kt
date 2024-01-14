package com.example.rickandmortyapi.data.network.converters

import android.content.LocusId
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResult
import com.example.rickandmortyapi.data.network.responseModels.SingleEpisodeResponse
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import org.junit.Assert.*

import org.junit.Test

//class EpisodeResponseConvertersKtTest {
//
//    private lateinit var singleEpisodeResponse: SingleEpisodeResponse
//    private lateinit var episodesResponse: EpisodesResponse
//
//    private lateinit var episodeModel : EpisodeModel
//
//    private lateinit var episodeDetailsModel : EpisodeDetailsModel
//
//    private lateinit var charactersList: List<CharacterModel>
//
//    private fun initNullableData(){
//        singleEpisodeResponse = SingleEpisodeResponse(null, null,
//            null, null)
//        episodeModel = EpisodeModel()
//        episodesResponse = EpisodesResponse(null, listOf(
//            EpisodesResult(
//                null, emptyList(), null, null, null
//            )
//        ))
//        episodeDetailsModel = EpisodeDetailsModel()
//        charactersList = emptyList()
//    }
//
//    private fun initRegularData(){
//        singleEpisodeResponse = SingleEpisodeResponse(2, "name",
//            "episode", listOf("character/1", "character/2"))
//        episodeModel = EpisodeModel(2, "name", "episode")
//        charactersList = listOf(
//            CharacterModel(
//                "24.10.23",
//                "male", 1, "url", CharacterModelLocation(),
//                "name", CharacterModelOrigin(), "species",
//                "status", "type", "url"
//            ),
//            CharacterModel(
//                "24.02.23",
//                "male", 2, "url", CharacterModelLocation(),
//                "name", CharacterModelOrigin(), "species",
//                "status", "type", "url"
//            )
//        )
//        episodesResponse = EpisodesResponse(null, listOf(
//            EpisodesResult(
//                null, listOf("character/1", "character/2"),
//                "episode", 2, "name"
//            )
//        ))
//        episodeDetailsModel = EpisodeDetailsModel(2, false,
//            "name", "episode", listOf(1, 2))
//
//
//    }
//
//    @Test
//    fun responseToEpisodeDomainModelWithNullableDataTest() {
//        initNullableData()
//        val executionResult = singleEpisodeResponse.toEpisodeDomainModel()
//        assertEquals(episodeModel, executionResult)
//    }
//
//    @Test
//    fun responseToEpisodeDomainModelWithRegularDataTest() {
//        initRegularData()
//        val executionResult = singleEpisodeResponse.toEpisodeDomainModel()
//        assertEquals(episodeModel, executionResult)
//    }
//
//    @Test
//    fun responseToEpisodeDetailsModelWithNullableDataTest() {
//        initNullableData()
//        val executionResult = singleEpisodeResponse.toEpisodeDetailsModel()
//        assertEquals(episodeDetailsModel, executionResult)
//    }
//
//    @Test
//    fun responseToEpisodeDetailsModelWithRegularDataTest() {
//        initRegularData()
//        val executionResult = singleEpisodeResponse.toEpisodeDetailsModel()
//        assertEquals(episodeDetailsModel, executionResult)
//    }
//
//    @Test
//    fun appendCharactersListWithEmptyListTest() {
//        initNullableData()
//        episodeDetailsModel.appendCharactersList(charactersList)
//        val executionResult = episodeDetailsModel.characters
//        assertEquals(charactersList, executionResult)
//    }
//
//    @Test
//    fun appendCharactersListWithRegularDataTest() {
//        initRegularData()
//        episodeDetailsModel.appendCharactersList(charactersList)
//        val executionResult = episodeDetailsModel.characters
//        assertEquals(charactersList, executionResult)
//    }
//
//    @Test
//    fun responseToEpisodeModelListWithNullableDataTest() {
//        initNullableData()
//        val executionResult = episodesResponse
//            .toEpisodeModelList().firstOrNull()
//        assertEquals(episodeModel, executionResult)
//    }
//
//    @Test
//    fun responseToEpisodeModelListWithRegularDataTest() {
//        initRegularData()
//        val executionResult = episodesResponse
//            .toEpisodeModelList().firstOrNull()
//        assertEquals(episodeModel, executionResult)
//    }
//
//
//}
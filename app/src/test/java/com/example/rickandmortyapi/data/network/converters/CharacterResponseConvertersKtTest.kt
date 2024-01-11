package com.example.rickandmortyapi.data.network.converters

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.CharactersResult
import com.example.rickandmortyapi.data.network.responseModels.ResponseLocation
import com.example.rickandmortyapi.data.network.responseModels.ResponseOrigin
import com.example.rickandmortyapi.data.network.responseModels.SingleCharacterResponse
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel
import org.junit.Assert.*

import org.junit.Test


class CharacterResponseConvertersKtTest {

    private lateinit var multiCharacterResponse: CharactersResponse

    private lateinit var characterModel: CharacterModel

    private lateinit var episodeModelsList: List<EpisodeModel>

    private var singleCharacterResponse: SingleCharacterResponse? = null

    private lateinit var characterDetailsModel: CharacterDetailsModel

    private lateinit var responseLocation: ResponseLocation

    private lateinit var responseOrigin: ResponseOrigin

    private lateinit var characterModelLocation: CharacterModelLocation

    private lateinit var characterModelOrigin: CharacterModelOrigin


    private fun setUpNullableInitialCharactersData() {
        responseLocation = ResponseLocation(null, null)
        characterModelLocation = CharacterModelLocation()
        responseOrigin = ResponseOrigin(null, null)
        characterModelOrigin = CharacterModelOrigin()
        multiCharacterResponse = CharactersResponse(
            null, listOf(
                CharactersResult(
                    null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null, null
                )
            )
        )

        singleCharacterResponse = SingleCharacterResponse(
            null,
            null, null, null,
            null, null, null,
            null, null, null,
            null, null
        )

        episodeModelsList = emptyList()

        characterDetailsModel = CharacterDetailsModel()

        characterModel = CharacterModel(
            "", "", 0,
            "", CharacterModelLocation("", ""),
            "", CharacterModelOrigin("", ""),
            "", "", "", ""
        )

    }

    private fun setUpRegularInitialCharactersData() {

        responseLocation = ResponseLocation("location", "url")

        responseOrigin = ResponseOrigin("origin", "url")

        multiCharacterResponse = CharactersResponse(
            null, listOf(
                CharactersResult(
                    "24.10.23", null, "male",
                    2, "url", responseLocation,
                    "name", responseOrigin, "species",
                    "status", "type", "url"
                )
            )
        )

        singleCharacterResponse = SingleCharacterResponse(
            "24.10.23", listOf("url/2", "url/5"), "male",
            2, "url", responseLocation,
            "name", responseOrigin, "species",
            "status", "type", "url"
        )

        characterModelLocation =
            CharacterModelLocation("location", "url")
        characterModelOrigin =
            CharacterModelOrigin("origin", "url")

        characterModel = CharacterModel(
            "24.10.23",
            "male", 2, "url", characterModelLocation,
            "name", characterModelOrigin, "species",
            "status", "type", "url"
        )

        episodeModelsList = listOf(
            EpisodeModel(2, "2", "2"), EpisodeModel(5, "5", "5")
        )

        characterDetailsModel = CharacterDetailsModel(
            created = "24.10.23", id = 2,
            gender = "male", episodeIds = listOf(2, 5),
            episode = episodeModelsList, url = "url",
            location = characterModelLocation,
            name = "name", origin = characterModelOrigin,
            species = "species", status = "status",
            type = "type", image = "url"
        )

    }


    @Test
    fun responseToDomainCharactersModelsListWithNullableDataTest() {
        setUpNullableInitialCharactersData()
        val executionResult = multiCharacterResponse
            .toDomainCharactersModelsList().firstOrNull()
        assertEquals(characterModel, executionResult)
    }

    @Test
    fun responseToDomainCharactersModelsListWithRegularDataTest() {
        setUpRegularInitialCharactersData()
        val executionResult = multiCharacterResponse.toDomainCharactersModelsList()
            .firstOrNull()
        assertEquals(characterModel, executionResult)
    }

    @Test
    fun singleCharacterResponseToCharacterDetailsDomainModelWithNullableDataTest() {
        setUpNullableInitialCharactersData()
        val executionResult = singleCharacterResponse
            ?.toCharacterDetailsDomainModel()
        assertEquals(characterDetailsModel, executionResult)
    }

    @Test
    fun singleCharacterResponseToCharacterDetailsDomainModelWithRegularDataTest() {
        setUpRegularInitialCharactersData()
        val executionResult = singleCharacterResponse
            ?.toCharacterDetailsDomainModel()
        assertEquals(characterDetailsModel, executionResult)
    }

    @Test
    fun singleCharacterResponseToCharacterModelWithNullableDataTest() {
        setUpNullableInitialCharactersData()
        val executionResult = singleCharacterResponse.toCharacterModel()
        assertEquals(characterModel, executionResult)
    }

    @Test
    fun singleCharacterResponseToCharacterModelWithRegularDataTest() {
        setUpRegularInitialCharactersData()
        val executionResult = singleCharacterResponse.toCharacterModel()
        assertEquals(characterModel, executionResult)
    }


    @Test
    fun appendEpisodesDetailsWithEmptyDataTest() {
        setUpNullableInitialCharactersData()
        characterDetailsModel
            .appendEpisodesDetails(episodeModelsList)
        val executionResult = characterDetailsModel
        assertEquals(episodeModelsList, executionResult.episode)
        assertEquals(episodeModelsList.size, executionResult.listSize)
    }

    @Test
    fun appendEpisodesDetailsWithRegularDataTest() {
        setUpRegularInitialCharactersData()
        characterDetailsModel
            .appendEpisodesDetails(episodeModelsList)
        val executionResult = characterDetailsModel
        assertEquals(episodeModelsList, executionResult.episode)
        assertEquals(episodeModelsList.size, executionResult.listSize)
    }

    @Test
    fun responseToCharacterLocationWithNullableDataTest() {
        setUpNullableInitialCharactersData()
        val executionResult = responseLocation
            .toCharacterLocation()
        assertEquals(characterModelLocation, executionResult)
    }

    @Test
    fun responseToCharacterLocationWithRegularDataTest() {
        setUpRegularInitialCharactersData()
        val executionResult = responseLocation
            .toCharacterLocation()
        assertEquals(characterModelLocation, executionResult)
    }

    @Test
    fun responseToCharacterOriginWithNullableData() {
        setUpNullableInitialCharactersData()
        val executionResult = responseOrigin
            .toCharacterOrigin()
        assertEquals(characterModelOrigin, executionResult)
    }

    @Test
    fun responseToCharacterOriginWithRegularData() {
        setUpRegularInitialCharactersData()
        val executionResult = responseOrigin
            .toCharacterOrigin()
        assertEquals(characterModelOrigin, executionResult)
    }

}
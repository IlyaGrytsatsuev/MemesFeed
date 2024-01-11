package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.CharacterDBLocation
import com.example.rickandmortyapi.data.db.entities.CharacterDBOrigin
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodes
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeModel
import org.junit.Assert.*

import org.junit.Test

class CharacterDetailsConvertersKtTest {

    private lateinit var characterDetailsModel: CharacterDetailsModel

    private lateinit var characterWithEpisodes: CharacterWithEpisodes

    private lateinit var characterEntity: CharacterEntity

    private lateinit var episodeList: List<EpisodeEntity>

    private lateinit var characterModelLocation: CharacterModelLocation

    private lateinit var characterModelOrigin: CharacterModelOrigin

    private lateinit var characterDBLocation: CharacterDBLocation

    private lateinit var characterDBOrigin: CharacterDBOrigin

    private lateinit var episodeModelsList: List<EpisodeModel>



    private fun initNullableData(){
        characterDetailsModel = CharacterDetailsModel()
        characterWithEpisodes = CharacterWithEpisodes()
        characterEntity = CharacterEntity()
        episodeList = emptyList()
    }

    private fun initRegularData(){
        characterModelLocation =
            CharacterModelLocation("location", "url")
        characterModelOrigin =
            CharacterModelOrigin("origin", "url")

        characterDBLocation =
            CharacterDBLocation("location", "url")
        characterDBOrigin =
            CharacterDBOrigin("origin", "url")

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

        episodeList = listOf(
            EpisodeEntity(2, "2", "2"),
            EpisodeEntity(5, "5", "5")
        )

        characterEntity = CharacterEntity(created = "24.10.23", id = 2,
            gender = "male", url = "url",
            location = characterDBLocation,
            name = "name", origin = characterDBOrigin,
            species = "species", status = "status",
            type = "type", image = "url")
        characterWithEpisodes = CharacterWithEpisodes(characterEntity, episodeList)


    }


    @Test
    fun domainModelToCharacterWithEpisodesDbModelWithNullableDataTest() {
        initNullableData()
        val executionResult = characterDetailsModel
            .toEpisodeWithCharactersDbModel()
        assertEquals(characterWithEpisodes, executionResult)
    }

    @Test
    fun domainModelToCharacterWithEpisodesDbModelWithRegularDataTest() {
        initRegularData()
        val executionResult = characterDetailsModel
            .toEpisodeWithCharactersDbModel()
        assertEquals(characterWithEpisodes, executionResult)
    }

    @Test
    fun dbModelToCharacterDetailsModelWithNullableDataTest() {
        initNullableData()
        val executionResult = characterWithEpisodes.toCharacterDetailsModel()
        assertEquals(characterDetailsModel, executionResult)
    }

    @Test
    fun dbModelToCharacterDetailsModelWithRegularDataTest() {
        initRegularData()
        val executionResult = characterWithEpisodes.toCharacterDetailsModel()
        assertEquals(characterDetailsModel, executionResult)
    }
}
package com.example.rickandmortyapi.data.db.converters

import com.example.rickandmortyapi.data.db.entities.CharacterDBLocation
import com.example.rickandmortyapi.data.db.entities.CharacterDBOrigin
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.CharacterModelLocation
import com.example.rickandmortyapi.domain.models.CharacterModelOrigin
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.utils.NullReceivedException
import org.junit.Assert

import org.junit.Test

class EpisodeDetailsConvertersKtTest {

    private lateinit var episodeDetailsModel : EpisodeDetailsModel

    private var episodeWithCharacters: EpisodeWithCharacters? = null

    private var episodeEntity: EpisodeEntity? = null

    private lateinit var charactersEntityList: List<CharacterEntity>

    private lateinit var charactersModelsList: List<CharacterModel>

    private var characterDBLocation: CharacterDBLocation? = null

    private var characterDBOrigin: CharacterDBOrigin? = null

    private lateinit var characterModelLocation: CharacterModelLocation

    private lateinit var characterModelOrigin: CharacterModelOrigin

    private fun initNullableData(){
        episodeDetailsModel = EpisodeDetailsModel.newEmptyInstance()
        episodeWithCharacters = null
    }

    private fun initRegularData(){

        episodeEntity = EpisodeEntity(2, "name", "episode")

        characterDBLocation =
            CharacterDBLocation("location", "url")
        characterDBOrigin =
            CharacterDBOrigin("origin", "url")

        characterModelLocation =
            CharacterModelLocation("location", "url")
        characterModelOrigin =
            CharacterModelOrigin("origin", "url")

        charactersEntityList = listOf(CharacterEntity(
            "24.10.23",
            "male", 1, "url", characterDBLocation!!,
            "name", characterDBOrigin!!, "species",
            "status", "type", "url"
        ),
            CharacterEntity(
                "24.02.23",
                "male", 2, "url", characterDBLocation!!,
                "name", characterDBOrigin!!, "species",
                "status", "type", "url"
            ))
        charactersModelsList = listOf(
            CharacterModel(
                "24.10.23",
                "male", 1, "url", characterModelLocation,
                "name", characterModelOrigin, "species",
                "status", "type", "url"
            ),
            CharacterModel(
                "24.02.23",
                "male", 2, "url", characterModelLocation,
                "name", characterModelOrigin, "species",
                "status", "type", "url"
            )
        )
        episodeWithCharacters = EpisodeWithCharacters(episodeEntity!!, charactersEntityList)
        episodeDetailsModel = EpisodeDetailsModel(2,
            "name", "episode", listOf(1, 2),
            charactersModelsList, charactersModelsList.size)
    }




    @Test
    fun domainModelToCharacterWithEpisodesDbModelWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeDetailsModel.toEpisodeWithCharactersDbModel()
        Assert.assertEquals(episodeWithCharacters, executionResult)
    }

    @Test(expected = NullReceivedException::class)
    fun episodeWithCharactersDbModelToEpisodeDetailsDomainModelWithNullableDataTest() {
        initNullableData()
        val executionResult = episodeWithCharacters.toEpisodeDetailsModel()
    }

    @Test
    fun episodeWithCharactersDbModelToEpisodeDetailsDomainModelWithRegularDataTest() {
        initRegularData()
        val executionResult = episodeWithCharacters.toEpisodeDetailsModel()
        Assert.assertEquals(episodeDetailsModel, executionResult)
    }
}
package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesUrlsEntity
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import javax.inject.Inject

class UpsertCharactersIntoDbUsecase @Inject constructor (private val dbRepository: CharactersDbRepository){

    suspend fun execute(character: CharacterModel? = null,
                        characterList: List<CharacterModel>? = null){
        dbRepository.upsertCharatersIntoDb(character, characterList)
    }
}
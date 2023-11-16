package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.utils.State
import javax.inject.Inject

class GetCharactersFromDbUsecase @Inject constructor
    (private val charactersDbRepository: CharactersDbRepository) {
    suspend fun execute() : State<List<CharacterModel>>
        = charactersDbRepository.getCharactersFromDB()

}
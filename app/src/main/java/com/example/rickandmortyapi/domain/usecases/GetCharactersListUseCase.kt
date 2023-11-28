package com.example.rickandmortyapi.domain.usecases

import android.util.Log
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(
    private val apiRepository: CharactersApiRepository) {

    suspend fun execute() : List<CharacterModel> {
        val resList = apiRepository.getCharactersList()
        Log.d("memesList", resList.toString())
        return resList
    }
}
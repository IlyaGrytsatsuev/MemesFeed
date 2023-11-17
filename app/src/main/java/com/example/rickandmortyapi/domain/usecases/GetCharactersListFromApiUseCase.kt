package com.example.rickandmortyapi.domain.usecases

import android.util.Log
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.utils.State
import javax.inject.Inject

class GetCharactersListFromApiUseCase @Inject constructor(
    private val apiRepository: CharactersApiRepository) {

    suspend fun execute(page:Int) : State<List<CharacterModel>> {
        val resList = apiRepository.getMemesListFromApi(page)
        Log.d("memesList", resList.toString())
        return resList
    }
}
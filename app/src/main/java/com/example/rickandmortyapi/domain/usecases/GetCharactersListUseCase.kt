package com.example.rickandmortyapi.domain.usecases

import android.util.Log
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(
    private val apiRepository: CharactersApiRepository) {

    suspend fun execute(name:String?, status: CharacterStatus?,
                        gender: CharacterGender?) : List<CharacterModel> {
        val resList = apiRepository.getCharactersList(
            name = name,
            status = status,
            gender = gender)
        Log.d("memesList", resList.toString())
        return resList
    }
    fun clearPaginationData(){
        apiRepository.clearPaginationData()
    }
}
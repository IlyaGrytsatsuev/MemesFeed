package com.example.rickandmortyapi.data.network.repository

import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.utils.State
import com.example.rickandmortyapi.utils.toDomainCharactersModelsList
import javax.inject.Inject

class CharactersApiRepositoryImpl @Inject constructor(private val memesApiService: CharactersApiService) : CharactersApiRepository {

    override suspend fun getMemesListFromApi(): State<List<CharacterModel>> {
        var charactersDomainModelList : List<CharacterModel> = listOf()
        try {
            val response = memesApiService.getCharactersList()
            if (response.isSuccessful) {
                val charactersRespModel = response.body()
                charactersDomainModelList =
                    charactersRespModel?.toDomainCharactersModelsList() ?: listOf()
                return State.NetworkSuccess(charactersDomainModelList)
            }
        }
        catch (e:Exception){
            return State.NetworkError(charactersDomainModelList, "Network error")
        }
        return State.NetworkError(charactersDomainModelList, "network error")
    }



}
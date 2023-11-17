package com.example.rickandmortyapi.data.network.repository

import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.utils.State
import com.example.rickandmortyapi.utils.toDomainCharactersModelsList
import com.example.rickandmortyapi.utils.toDomainModel
import javax.inject.Inject

class CharactersApiRepositoryImpl @Inject constructor(private val memesApiService: CharactersApiService) : CharactersApiRepository {

    override suspend fun getMemesListFromApi(page:Int): State<List<CharacterModel>> {
        var charactersDomainModelList : List<CharacterModel> = listOf()
        try {
            val response = memesApiService.getCharactersList(page)
            if (response.isSuccessful) {
                val charactersRespModel = response.body()
                val info = charactersRespModel?.info?.toDomainModel()
                charactersDomainModelList =
                    charactersRespModel?.toDomainCharactersModelsList() ?: listOf()
                return State.NetworkSuccess(data = charactersDomainModelList, info = info)
            }
        }
        catch (e:Exception){
            return State.NetworkError(charactersDomainModelList, "Network error")
        }
        return State.NetworkError(charactersDomainModelList, "network error")
    }



}
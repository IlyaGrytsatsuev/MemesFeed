package com.example.rickandmortyapi.data.network.repository

import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.utils.State
import com.example.rickandmortyapi.data.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.data.converters.toDomainModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import javax.inject.Inject



//todo()
//возврат списков из реплозиториев, стейты во вьюмодели , ошибки во вьюмодели , стейты в презентер, подсчет страниц в репозитория, хранение в синглтон классе,
// возрат model без обертки Response в service, кэширование в api репозитории
class CharactersApiRepositoryImpl @Inject constructor(private val memesApiService: CharactersApiService) : CharactersApiRepository {

    override suspend fun getMemesListFromApi(page:Int,
                                             name:String?,
                                             status: CharacterStatus?,
                                             gender: CharacterGender?): State<List<CharacterModel>> {
        var charactersDomainModelList : List<CharacterModel> = listOf()
        try {
            val response = memesApiService.getCharactersList(page, name,
                status?.text, gender?.text)
            if (response.isSuccessful) {

                val charactersRespModel = response.body()
                val info = charactersRespModel?.info?.toDomainModel()
                charactersDomainModelList =
                    charactersRespModel?.toDomainCharactersModelsList() ?: listOf()
                return State.NetworkSuccess(data = charactersDomainModelList, info = info)
            }
        }
        catch (e:Exception){
            return State.NetworkError(charactersDomainModelList, "Network exception")
        }
        return State.NetworkError(charactersDomainModelList, "network error")
    }



}
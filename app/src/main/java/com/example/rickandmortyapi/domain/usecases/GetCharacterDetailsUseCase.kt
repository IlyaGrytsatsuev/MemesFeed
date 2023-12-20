package com.example.rickandmortyapi.domain.usecases

import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject
constructor(private val apiRepository: CharactersApiRepository){

    suspend fun execute(id:Int): CharacterDetailsModel? {
        return apiRepository.getCharacterDetails(id)
    }


}
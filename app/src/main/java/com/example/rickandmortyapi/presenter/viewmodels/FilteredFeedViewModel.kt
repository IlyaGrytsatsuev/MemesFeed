package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapi.data.network.InternetConnectionChecker
import com.example.rickandmortyapi.domain.usecases.GetCharactersFromDbUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListFromApiUseCase
import com.example.rickandmortyapi.domain.usecases.UpsertCharactersIntoDbUsecase
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus

class FilteredFeedViewModel (private val getCharactersListFromApiUseCase: GetCharactersListFromApiUseCase,
                             private val internetConnectionChecker: InternetConnectionChecker,
                             private val getCharactersFromDbUsecase: GetCharactersFromDbUsecase,
                             private val upsertCharactersIntoDbUsecase: UpsertCharactersIntoDbUsecase)
    : ViewModel() {

    private var characterStatus: CharacterStatus = CharacterStatus.UNCHOSEN
    private var characterGender: CharacterGender = CharacterGender.UNCHOSEN

    fun getCharacterStatus() = characterStatus
    fun getCharacterGender() = characterGender
    fun setCharacterStatus(value:CharacterStatus){
        characterStatus = value
    }
    fun setCharacterGender(value:CharacterGender) {
        characterGender = value
    }



}
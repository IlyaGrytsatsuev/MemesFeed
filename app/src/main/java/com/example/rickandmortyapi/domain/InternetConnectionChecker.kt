package com.example.rickandmortyapi.domain

import android.content.Context
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.State

interface InternetConnectionChecker {
    fun isConnectedToInternet():State<List<CharacterModel>>

}
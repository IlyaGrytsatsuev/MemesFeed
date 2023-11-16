package com.example.rickandmortyapi.utils

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.domain.models.CharacterModel

sealed class State<T>(val data:T? = null, val message:String? = null){
    class DbSuccess<T>(data:T): State<T>(data)
    class NetworkSuccess<T>(data: T): State<T>(data)
    class NoInternet<T>(message: String): State <T>(message = message)
    class DbEmpty<T>(message: String): State<T>(message = message)
    class NetworkError<T>(data:T, message: String?): State<T>(data, message)
    class DbLoading<T>():State<T>()
    class NetworkLoading<T>():State<T>()

}

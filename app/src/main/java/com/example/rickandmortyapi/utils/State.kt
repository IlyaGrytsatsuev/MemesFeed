package com.example.rickandmortyapi.utils

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.InfoModel

sealed class State<T>(val data:T? = null, val info:InfoModel? = null, val message:String? = null){
    class DbSuccess<T>(data:T): State<T>(data = data)
    class NetworkSuccess<T>(data: T, info:InfoModel?): State<T>(data = data, info = info)
    class NoInternet<T>(message: String): State <T>(message = message)
    class DbEmpty<T>(message: String): State<T>(message = message)
    class NetworkError<T>(data:T, message: String?): State<T>(data = data, message = message)
    class DbLoading<T>:State<T>()
    class NetworkLoading<T>:State<T>()

}

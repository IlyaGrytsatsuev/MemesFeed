package com.example.rickandmortyapi.presenter

sealed class State<T>(val data:T? = null){
    class Success<T>(data:T): State<T>(data = data)
    class Empty<T>: State<T>()
    class Error<T>: State<T>()
    class Loading<T>: State<T>()

}

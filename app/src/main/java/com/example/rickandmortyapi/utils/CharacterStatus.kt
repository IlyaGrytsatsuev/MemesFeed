package com.example.rickandmortyapi.utils

enum class CharacterStatus (val text:String?){
    UNCHOSEN(null),
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown"),
}

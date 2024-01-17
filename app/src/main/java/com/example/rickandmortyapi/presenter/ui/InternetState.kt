package com.example.rickandmortyapi.presenter.ui

sealed class InternetState (){
    class InternetLost: InternetState()
    class InternetRestored: InternetState()
    class HasInternet: InternetState()
    class NoInternet: InternetState()
}



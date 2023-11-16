package com.example.rickandmortyapi.domain.usecases

import android.content.Context
import com.example.rickandmortyapi.domain.InternetConnectionChecker
import javax.inject.Inject

class CheckInternetConnectionUsecase
@Inject constructor(
    private val internetConnectionChecker: InternetConnectionChecker) {

    fun execute() = internetConnectionChecker.isConnectedToInternet()

}
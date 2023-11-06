package com.example.memesfeed.domain.usecases

import android.util.Log
import com.example.memesfeed.domain.models.MemeModel
import com.example.memesfeed.domain.repository.MemesApiRepository
import javax.inject.Inject

class GetMemesListFromApiUseCase @Inject constructor(
    private val apiRepository: MemesApiRepository) {

    suspend fun execute() : List<MemeModel>{
        val resList = apiRepository.getMemesListFromApi()
        Log.d("memesList", resList.toString())
        return resList
    }
}
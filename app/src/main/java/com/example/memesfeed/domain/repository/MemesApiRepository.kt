package com.example.memesfeed.domain.repository

import com.example.memesfeed.domain.models.MemeModel

interface MemesApiRepository {

    suspend fun getMemesListFromApi():List<MemeModel>
}
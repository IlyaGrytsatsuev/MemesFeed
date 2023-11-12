package com.example.rickandmortyapi.domain.repository

interface MemesApiRepository {

    suspend fun getMemesListFromApi():List<MemeModel>
}
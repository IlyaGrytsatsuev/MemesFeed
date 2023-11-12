package com.example.rickandmortyapi.data.network.repository

import com.example.rickandmortyapi.data.network.service.MemesApiService
import com.example.rickandmortyapi.domain.repository.MemesApiRepository
import javax.inject.Inject

class MemesApiRepositoryImpl @Inject constructor(private val memesApiService: MemesApiService) : MemesApiRepository {

    override suspend fun getMemesListFromApi(): List<MemeModel> {
        var memesDomainModelList : List<MemeModel> = listOf()
        val response = memesApiService.getMemesList()
        if(response.isSuccessful) {
            val memesApiList = response.body()?.data
            memesDomainModelList =
                memesApiList?.copyToDomainModelsList() ?: listOf()
        }
        return memesDomainModelList
    }

    private fun Data.copyToDomainModelsList(): MutableList<MemeModel> {
        val result: MutableList<MemeModel> = mutableListOf()
        this.memes?.forEach {
            val memeModel = MemeModel(
                boxCount = it.boxCount?:0,
                captions = it.captions?:0,
                height = it.height?:0,
                id = it.id?:"",
                name = it.name?: "",
                url = it.url?: "",
                width = it.width?:0
            )
            result.add(memeModel)
        }
        //Log.d("respBody", result.toString())
        return result
    }

}
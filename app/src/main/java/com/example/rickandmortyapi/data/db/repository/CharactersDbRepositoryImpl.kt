package com.example.rickandmortyapi.data.db.repository

import android.util.Log
import com.example.rickandmortyapi.data.converters.toCharacterDetailsModel
import com.example.rickandmortyapi.data.db.dao.CharacterDao
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.data.converters.toCharacterModel
import com.example.rickandmortyapi.data.converters.toCharacterWithEpisodesDbModel
import com.example.rickandmortyapi.data.converters.toDbEntity
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import com.example.rickandmortyapi.utils.Constants
import javax.inject.Inject

class CharactersDbRepositoryImpl @Inject constructor
    (private val characterDao: CharacterDao,
     private val paginationDataRepository: PaginationDataRepository

) : CharactersDbRepository {
    override suspend fun getCharactersFromDB(
        id:Int?,
        name:String?, status:CharacterStatus?,
        gender: CharacterGender?)
    : List<CharacterModel> {
        val limit = Constants.ITEMS_PER_PAGE
        val offset = limit*(paginationDataRepository.getCurPage()-1)
        val charactersDbList = characterDao
            .getCharacters(
                limit = limit,
                offset = offset,
                name = name,
                status =  status?.text,
                gender = gender?.text
            )
        val characterModelsList = mutableListOf<CharacterModel>()
        if(charactersDbList.isNotEmpty())
            charactersDbList.forEach {
                characterModelsList.add(it.toCharacterModel())
            }
        Log.d("netList", "db list = $charactersDbList")
        return characterModelsList
    }

    override suspend fun upsertCharactersIntoDb(
        characterList: List<CharacterModel>?
    ) {
        val list :MutableList<CharacterEntity> = mutableListOf()
        characterList?.forEach {
            list.add(it.toDbEntity())
        }
        characterDao.upsertCharacterEntity(list)

    }

    override suspend fun getCharacterWithEpisodesByIdFromDB(id: Int): CharacterDetailsModel? {
        return characterDao.getCharactersWithEpisodesById(id)?.toCharacterDetailsModel()
    }

    override suspend fun upsertCharacterWithEpisodesIntoDb
                (characterDetailsModel: CharacterDetailsModel) {
        characterDao.upsertListOfCharacterWithEpisodes(
            characterDetailsModel.toCharacterWithEpisodesDbModel())
    }
}
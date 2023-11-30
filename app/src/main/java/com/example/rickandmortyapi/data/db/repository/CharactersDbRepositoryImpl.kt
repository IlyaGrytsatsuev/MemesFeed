package com.example.rickandmortyapi.data.db.repository

import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.db.dao.CharacterDao
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodesUrls
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.data.converters.toCharacterModel
import com.example.rickandmortyapi.data.converters.toDbEntity
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import com.example.rickandmortyapi.utils.Constants
import javax.inject.Inject

class CharactersDbRepositoryImpl @Inject constructor
    (private val characterDao: CharacterDao,
     private val paginationData: PaginationData
) : CharactersDbRepository {
    override suspend fun getCharactersFromDB(
        name:String?, status:CharacterStatus?,
        gender: CharacterGender?)
    : List<CharacterModel> {
        val limit = Constants.ITEMS_PER_PAGE
        val offset = limit*(paginationData.curPage-1)
        val charactersDbList = characterDao
            .getCharactersWithEpisodesUrls(limit = limit,
                offset = offset,
                name = name,
                status =  status?.text,
                gender = gender?.text)
        val characterModelsList = mutableListOf<CharacterModel>()
        if(charactersDbList.isNotEmpty())
            charactersDbList.forEach {
                characterModelsList.add(it.toCharacterModel())
            }
        return characterModelsList
    }

    override suspend fun upsertCharatersIntoDb(
        character: CharacterModel?,
        characterList: List<CharacterModel>?
    ) {
        if(character != null)
            characterDao.upsertCharacterWithEpisodesUrls(character.toDbEntity())
        else{
            val list :MutableList<CharacterWithEpisodesUrls> = mutableListOf()
            characterList?.forEach {
                list.add(it.toDbEntity())
            }
            characterDao.upsertListOfCharactersWithUrls(list)
        }
    }
}
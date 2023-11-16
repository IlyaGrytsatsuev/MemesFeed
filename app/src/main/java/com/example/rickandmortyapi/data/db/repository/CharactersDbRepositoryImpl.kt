package com.example.rickandmortyapi.data.db.repository

import android.util.Log
import com.example.rickandmortyapi.data.db.dao.CharacterDao
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodesUrls
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesUrlsEntity
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.utils.State
import com.example.rickandmortyapi.utils.toCharacterModel
import com.example.rickandmortyapi.utils.toDbEntity
import javax.inject.Inject

class CharactersDbRepositoryImpl @Inject constructor
    (private val characterDao: CharacterDao) : CharactersDbRepository {
    override suspend fun getCharactersFromDB(): State<List<CharacterModel>> {
        val charactersDbList = characterDao.getCharactersWithEpisodesUrls()
        val characterModelsList = mutableListOf<CharacterModel>()
        if(charactersDbList.isEmpty())
            return State.DbEmpty("List is empty")
        charactersDbList.forEach {
            characterModelsList.add(it.toCharacterModel())
        }
        //Log.d("listDB", charactersDbList.toString())
        return State.DbSuccess(characterModelsList)
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
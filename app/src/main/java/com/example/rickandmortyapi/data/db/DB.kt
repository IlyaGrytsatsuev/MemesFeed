package com.example.rickandmortyapi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortyapi.data.db.dao.CharacterDao
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesUrlsEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeUrlEntity


@Database(entities = [CharacterEntity::class,
    EpisodeUrlEntity::class,
    CharactersAndEpisodesUrlsEntity::class], version = 1 )
abstract class DB : RoomDatabase() {
    abstract fun  characterDao(): CharacterDao
}
package com.example.rickandmortyapi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortyapi.data.db.dao.CharacterDao
import com.example.rickandmortyapi.data.db.dao.EpisodeDao
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesIdsEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity


@Database(entities = [CharacterEntity::class,
    EpisodeEntity::class,
    CharactersAndEpisodesIdsEntity::class], version = 1 )
abstract class DB : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao() :EpisodeDao
}
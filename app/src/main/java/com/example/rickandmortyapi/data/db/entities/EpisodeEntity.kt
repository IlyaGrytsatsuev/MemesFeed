package com.example.rickandmortyapi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "episodeId")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name:String = "",
    @ColumnInfo(name = "episode")
    val episode:String = ""
)

data class EpisodeWithCharacters(
    @Embedded
    val episodeEntity: EpisodeEntity = EpisodeEntity(),
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharactersAndEpisodesIdsEntity::class)
    )
    val characters: List<CharacterEntity> = emptyList()
)
package com.example.rickandmortyapi.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["characterId", "episodeId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["characterId"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EpisodeEntity::class,
            parentColumns = ["episodeId"],
            childColumns = ["episodeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]  )
data class CharactersAndEpisodesIdsEntity(
    val characterId: Int = 0,
    val episodeId: Int = 0
)
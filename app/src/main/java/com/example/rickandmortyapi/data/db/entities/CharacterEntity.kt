package com.example.rickandmortyapi.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class CharacterEntity (
    @ColumnInfo(name = "created")
    val created: String = "",
    @ColumnInfo(name = "gender")
    val gender: String = "",
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "characterId")
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val image: String = "",
    @Embedded
    val location: CharacterLocation = CharacterLocation(),
    @ColumnInfo(name = "name")
    val name: String = "",
    @Embedded
    val origin: CharacterOrigin = CharacterOrigin(),
    @ColumnInfo(name = "species")
    val species: String = "",
    @ColumnInfo(name = "status")
    val status: String = "",
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "url")
    val url: String = ""
)

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

data class CharacterWithEpisodes(
    @Embedded
    val characterEntity: CharacterEntity = CharacterEntity(),
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(CharactersAndEpisodesIdsEntity::class)
    )
    val episodes: List<EpisodeEntity> = emptyList()
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



data class CharacterLocation(
    @ColumnInfo(name = "location_name")
    val name: String = "",
    @ColumnInfo(name = "location_url")
    val url: String = ""
)

data class CharacterOrigin(
    @ColumnInfo(name = "origin_name")
    val name: String = "",
    @ColumnInfo(name = "origin_url")
    val url: String = ""
)



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
data class EpisodeUrlEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "episodeUrl")
    val url: String = ""
)


@Entity(primaryKeys = ["characterId", "episodeUrl"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["characterId"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EpisodeUrlEntity::class,
            parentColumns = ["episodeUrl"],
            childColumns = ["episodeUrl"],
            onDelete = ForeignKey.CASCADE
        )
    ]  )
data class CharactersAndEpisodesUrlsEntity(
    val characterId: Int = 0,
    val episodeUrl: String = ""
)

data class CharacterWithEpisodesUrls(
    @Embedded
    val characterEntity: CharacterEntity = CharacterEntity(),
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeUrl",
        associateBy = Junction(CharactersAndEpisodesUrlsEntity::class)
    )
    val episodesUrls: List<EpisodeUrlEntity> = emptyList()
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


